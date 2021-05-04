package com.shop.model.exception;

public class InvalidDataRuntimeException extends RuntimeException {
    public InvalidDataRuntimeException() {
    }

    public InvalidDataRuntimeException(String message) {
        super(message);
    }

    public InvalidDataRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
