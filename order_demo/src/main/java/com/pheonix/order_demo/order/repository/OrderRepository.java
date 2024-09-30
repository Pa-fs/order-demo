package com.pheonix.order_demo.order.repository;

import com.pheonix.order_demo.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
