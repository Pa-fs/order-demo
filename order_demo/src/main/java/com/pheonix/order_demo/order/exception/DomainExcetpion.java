package com.pheonix.order_demo.order.exception;

public class DomainExcetpion extends RuntimeException {

    public DomainExcetpion(String message) {
        super(message);
    }

    public DomainExcetpion(String message, Throwable cause) {
        super(message, cause);
    }
}
