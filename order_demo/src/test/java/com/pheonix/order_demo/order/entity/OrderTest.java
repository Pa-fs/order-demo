package com.pheonix.order_demo.order.entity;

import com.pheonix.order_demo.order.exception.OrderDomainException;
import com.pheonix.order_demo.order.repository.MemberRepository;
import com.pheonix.order_demo.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

//    @BeforeEach
    @BeforeAll
    public void setUp() {
        Member member = new Member();
        member.setName("test1");
        memberRepository.save(member);
    }

    @Test
    public void 회원_저장_및_주문_생성_테스트() {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new OrderDomainException("not found member"));
        assertThat(member.getId()).isNotNull();


        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setMember(member);
        Order savedOrder = orderRepository.save(order);

        // 주문 ID 및 회원 연관관계 검증
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    public void 주문_취소_테스트() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.READY);
        order.setMember(memberRepository.getById(1L));
        orderRepository.save(order);

        log.info("order object status : {}", order.getOrderStatus());

        order.cancel();

        orderRepository.findAll().forEach(o -> log.info("orderId : {}", o.getId()));

        Order foundOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderDomainException("not found order"));

        assertThat(order.getMember().getId()).isNotNull();

        log.info("After canceling, order object status : {}", foundOrder.getOrderStatus());
        log.info("foundOrder object status : {}", foundOrder.getOrderStatus());

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(foundOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());
    }
}
