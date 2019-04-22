package com.example.demo.exception;

import com.example.demo.enums.ErrorCodesEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jbelligund001 on 2/17/2016.
 */
@Getter
@Slf4j
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -6248135062089889915L;

    private final ErrorDetails errorInfo;

    public AppException(String errText, String errorCode) {
        errorInfo = ErrorDetails.builder()
                .errorCode(errorCode)
                .errorText(errText)
                .build();
    }


    public AppException(ErrorCodesEnum errorCode, String errText, String ... args) {
        errorInfo = ErrorDetails.builder()
                .errorCode(errorCode.toString())
                .errorText(errText)
                .args(args)
                .build();
    }

    public AppException(String errText, ErrorCodesEnum errorCode) {
        errorInfo = ErrorDetails.builder()
                .errorCode(errorCode.toString())
                .errorText(errText)
                .build();
    }

    @Getter
    @Builder
    public static class ErrorDetails implements Serializable {

        private static final long serialVersionUID = -3293104260432165591L;

        private String errorCode;

        private String errorText;

        private String[] args;

        private String suppressedErrCode;

        private String suppressedErrMsg;

        private Map<String, String> suppressedErrDetails = new HashMap<>();

        private Throwable cause;

        {
            ReflectionToStringBuilder.toStringExclude(this, "cause");
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }
}