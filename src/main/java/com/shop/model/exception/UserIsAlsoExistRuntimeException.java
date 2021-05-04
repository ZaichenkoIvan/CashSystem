package com.shop.model.exception;

public class UserIsAlsoExistRuntimeException extends RuntimeException {
    public UserIsAlsoExistRuntimeException() {
    }

    public UserIsAlsoExistRuntimeException(String message) {
        super(message);
    }

    public UserIsAlsoExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
