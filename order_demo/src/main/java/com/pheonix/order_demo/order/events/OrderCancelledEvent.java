package com.pheonix.order_demo.order.events;

public class OrderCancelledEvent {

    private final Long orderNumber;
    public OrderCancelledEvent(Long id) {
        this.orderNumber = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }
}
