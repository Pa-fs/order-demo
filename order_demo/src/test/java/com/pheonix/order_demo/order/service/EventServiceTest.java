package com.pheonix.order_demo.order.service;

import com.pheonix.order_demo.order.entity.Event;
import com.pheonix.order_demo.order.entity.Member;
import com.pheonix.order_demo.order.lock.LockAdapter;
import com.pheonix.order_demo.order.repository.EventRepository;
import com.pheonix.order_demo.order.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class EventServiceTest {

    @Autowired
    private LockAdapter lockAdapter;

    @Autowired
    private EventService eventService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EventRepository eventRepository;

    final Long firstUserId = 1L;
    final Long secondUserId = 2L;
    final Long thirdUserId = 3L;

    @Test
    @DisplayName("firstUserId가 락을 선점한다.")
    public void testLock() {
        final Long eventId = 123123123L;

        Boolean isSuccess = lockAdapter.holdLock(eventId, firstUserId);
        assertThat(isSuccess).isTrue();

        isSuccess = lockAdapter.holdLock(eventId, secondUserId);
        assertThat(isSuccess).isFalse();

        isSuccess = lockAdapter.holdLock(eventId, thirdUserId);
        assertThat(isSuccess).isFalse();

        Long holderId = lockAdapter.checkLock(eventId);
        log.info("holderId in lock : {}", holderId);
        assertThat(firstUserId).isEqualTo(holderId);
    }


//    @Test
//    @DisplayName("3명이 동시에 락을 선점하지만 1명만 락을 얻는다, 결과는 항상 다르다")
//    public void testConcurrentTest() throws InterruptedException {
//
//        final Long eventId = 999999L;
//        lockAdapter.clearLock(eventId);
//
//        // CyclicBarrier 쓰레드 수에 대한 관심사
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
//        new Thread(new Accessor(eventId, firstUserId, cyclicBarrier)).start();
//        new Thread(new Accessor(eventId, secondUserId, cyclicBarrier)).start();
//        new Thread(new Accessor(eventId, thirdUserId, cyclicBarrier)).start();
//        TimeUnit.SECONDS.sleep(1);
//
//        Long holderId = lockAdapter.checkLock(eventId);
//        log.info("선점한 유저 아이디 : {}", holderId);
//        Assertions.assertEquals(firstUserId, holderId);
//    }

    @Test
    // 영속성 컨텍스트가 스레드 간에 공유되지 않기 때문에 메인 스레드(@Test)에서 트랜잭션을 걸면 안된다
    // @Transactional
    @DisplayName("3명이 동시에 락을 선점하지만 1명만 락을 얻는다, 결과는 항상 다르다")
    public void testEvent() throws InterruptedException {

        final Long eventId = 1L;
        lockAdapter.clearLock(eventId);

        Member member1 = new Member();
        member1.setName("test1");
        Member member2 = new Member();
        member2.setName("test2");
        Member member3 = new Member();
        member3.setName("test3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Event event = new Event();
        event.setName("event1");
        eventRepository.save(event);
        TimeUnit.SECONDS.sleep(1);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(new Accessor(event.getId(), member1.getId(), cyclicBarrier)).start();
        new Thread(new Accessor(event.getId(), member2.getId(), cyclicBarrier)).start();
        new Thread(new Accessor(event.getId(), member3.getId(), cyclicBarrier)).start();
        TimeUnit.SECONDS.sleep(1);

        Long winnerMemberId = eventRepository.findById(event.getId()).orElse(null).getWinnerMemberId();
        log.info("winner : {}", winnerMemberId);
        // 당첨 결과는 항상 달라진다 => 테스트 결과가 항상 달라진다
        assertThat(winnerMemberId).isEqualTo(member3.getId());
    }

    class Accessor implements Runnable {

        private Long eventId;
        private Long userId;
        private CyclicBarrier cyclicBarrier;

        public Accessor(Long eventId, Long userId, CyclicBarrier cyclicBarrier) {
            this.eventId = eventId;
            this.userId = userId;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            log.info("{}, 쓰레드 작업 중", Thread.currentThread().getName());
            try {
                // CyclicBarrier는 주어진 수의 쓰레드가 모두 await를 호출할 때까지 대기함, 마지막 쓰레드가 도착하면 모든 쓰레드는 동시에 깨어난다
                // 한 쓰레드가 인터럽트되거나 타임아웃되면 그 쓰레드에 예외가 발생하고 모든 대기 중인 쓰레드도 예외가 발생하여 대기가 해제됨.
                cyclicBarrier.await();
                log.info("{}, 대기에서 풀림", Thread.currentThread().getName());
                eventService.attendEvent(eventId, userId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}