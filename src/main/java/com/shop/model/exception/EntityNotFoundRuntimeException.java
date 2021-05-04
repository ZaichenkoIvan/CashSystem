package com.shop.model.exception;

public class EntityNotFoundRuntimeException extends RuntimeException {
    public EntityNotFoundRuntimeException() {
    }

    public EntityNotFoundRuntimeException(String message) {
        super(message);
    }

    public EntityNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

