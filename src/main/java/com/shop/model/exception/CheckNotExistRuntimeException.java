package com.shop.model.exception;

public class CheckNotExistRuntimeException extends RuntimeException {
    public CheckNotExistRuntimeException() {
    }

    public CheckNotExistRuntimeException(String message) {
        super(message);
    }

    public CheckNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
