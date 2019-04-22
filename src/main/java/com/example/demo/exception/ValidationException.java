package com.example.demo.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Generic Exception thrown from the system in case the request passed to it is invalid
 * Created by jbelligund001 on 2/17/2016.
 */
@Slf4j
public class ValidationException extends RuntimeException {

    @Getter
    private final String errMsg;

    @Getter
    private final String [] args ;

    public ValidationException(String errMsg) {
        this(errMsg , new String[0]);
    }

    public ValidationException(String errMsg, String ... args) {
        super(errMsg);
        this.errMsg = errMsg;
        this.args = args;
    }
}