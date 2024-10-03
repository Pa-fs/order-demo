package com.pheonix.order_demo.order.service;

import com.pheonix.order_demo.order.entity.Order;
import com.pheonix.order_demo.order.entity.OrderStatus;
import com.pheonix.order_demo.order.exception.DomainExcetpion;
import com.pheonix.order_demo.order.exception.OrderDomainException;
import com.pheonix.order_demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefundService {

    private final OrderRepository orderRepository;

    @Transactional
    public void refund(Long orderNumber) {
        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new OrderDomainException("해당 주문번호는 찾을 수 없습니다."));
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("주문번호 {} 가 정상적으로 취소 처리되었습니다", orderNumber);
    }
}


