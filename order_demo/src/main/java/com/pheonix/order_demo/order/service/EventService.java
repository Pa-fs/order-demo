package com.pheonix.order_demo.order.service;

import com.pheonix.order_demo.order.entity.Event;
import com.pheonix.order_demo.order.entity.Member;
import com.pheonix.order_demo.order.exception.DomainExcetpion;
import com.pheonix.order_demo.order.lock.LockAdapter;
import com.pheonix.order_demo.order.repository.EventRepository;
import com.pheonix.order_demo.order.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {
    private final LockAdapter lockAdapter;
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Transactional(timeout = 10)
    public Boolean attendEvent(Long eventId, Long userId) {

        if (!lockAdapter.holdLock(eventId, userId)) {
            return Boolean.FALSE;
        }

        Event eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new DomainExcetpion("No eventId"));

        if (eventEntity.nonEmptyUser()) {
            return Boolean.FALSE;
        }

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainExcetpion("No user"));
        eventEntity.winner(member.getId());
        eventRepository.save(eventEntity);
        return Boolean.TRUE;
    }

}
