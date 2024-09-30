package com.pheonix.order_demo.order.entity;

import com.pheonix.order_demo.order.repository.MemberRepository;
import com.pheonix.order_demo.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA관련 컴포넌트만 테스트, 기본적으로 인메모리 데이터베이스를 사용, 트랜잭션 지원
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용하도록 설정
class MemberAndOrderTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 회원_저장_및_주문_생성_테스트() {
        Member member = new Member();
        member.setName("test1");
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();

        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setMember(savedMember);
        Order savedOrder = orderRepository.save(order);

        // 주문 ID 및 회원 연관관계 검증
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getMember().getId()).isEqualTo(savedMember.getId());
    }
}
