package com.shop.model.exception;

public class GoodsIsExistRuntimeException extends RuntimeException {
    public GoodsIsExistRuntimeException() {
    }

    public GoodsIsExistRuntimeException(String message) {
        super(message);
    }

    public GoodsIsExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
