package com.pheonix.order_demo.order.events;

import com.pheonix.order_demo.order.service.RefundService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCancelledEventHandler {
    private final RefundService refundService;

    public OrderCancelledEventHandler(RefundService refundService) {
        this.refundService = refundService;
    }

    @EventListener(OrderCancelledEvent.class)
    public void handle(OrderCancelledEvent event) {
        refundService.refund(event.getOrderNumber());
    }
}
