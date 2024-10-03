package com.pheonix.order_demo.order.events;

import com.pheonix.order_demo.order.service.RefundService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderCancelledEventHandler {
    private final RefundService refundService;

    public OrderCancelledEventHandler(RefundService refundService) {
        this.refundService = refundService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener(OrderCancelledEvent.class)
    public void handle(OrderCancelledEvent event) {
        refundService.refund(event.getOrderNumber());
    }
}
