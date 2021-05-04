package com.shop.controller;

import com.shop.model.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({SQLException.class,
            NullPointerException.class,
            CheckNotExistRuntimeException.class,
            EntityNotFoundRuntimeException.class,
            GoodsIsExistRuntimeException.class,
            InvalidDataRuntimeException.class,
            InvalidIdRuntimeException.class,
            OrderNotExistRuntimeException.class,
            IllegalArgumentException.class,
            Throwable.class})
    public String handleException() {
        return "errorSpring";
    }
}
