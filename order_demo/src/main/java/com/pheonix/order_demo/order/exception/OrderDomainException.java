package com.pheonix.order_demo.order.exception;

public class OrderDomainException extends DomainExcetpion {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
