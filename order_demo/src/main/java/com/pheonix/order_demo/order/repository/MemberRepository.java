package com.pheonix.order_demo.order.repository;

import com.pheonix.order_demo.order.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
