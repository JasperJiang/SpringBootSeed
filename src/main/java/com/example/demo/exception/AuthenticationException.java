package com.example.demo.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Generic Exception thrown from the system in case the requested object is not found
 * Created by jbelligund001 on 2/17/2016.
 */
@Getter
@Slf4j
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 6947875820576271402L;

    private final String errMsg;

    private final String[] args;

    public AuthenticationException(String errText, String... parameters) {
        super(errText);
        this.errMsg = errText;
        this.args = parameters;
    }

}