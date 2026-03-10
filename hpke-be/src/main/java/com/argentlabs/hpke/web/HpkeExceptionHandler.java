package com.argentlabs.hpke.web;

import com.argentlabs.hpke.hpke.HpkeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HpkeExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HpkeException.class)
    public HpkeStatusResponse handleHpkeException(HpkeException exception) {
        return new HpkeStatusResponse(exception.getMessage());
    }
}
