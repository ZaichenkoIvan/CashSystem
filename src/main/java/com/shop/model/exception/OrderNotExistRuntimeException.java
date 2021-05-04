package com.shop.model.exception;

public class OrderNotExistRuntimeException extends RuntimeException {
    public OrderNotExistRuntimeException() {
    }

    public OrderNotExistRuntimeException(String message) {
        super(message);
    }

    public OrderNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
