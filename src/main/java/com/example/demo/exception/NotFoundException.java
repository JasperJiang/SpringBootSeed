package com.example.demo.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Generic Exception thrown from the system in case the requested object is not found
 * Created by jbelligund001 on 2/17/2016.
 */
@Getter
@Slf4j
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7257439206586798173L;

    private final String errMsg;

    private final String suppressedMsg;

    private final String[] args;

    public NotFoundException(String errText, String... parameters) {
        super(errText);
        this.errMsg = errText;
        this.suppressedMsg = errText + " Parameters:[ " + StringUtils.join(parameters, ",") + " ]";
        this.args = parameters;
    }

}