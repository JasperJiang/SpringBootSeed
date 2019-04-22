package com.example.demo.controller;

import com.example.demo.exception.*;
import com.example.demo.vo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private ErrorResponse getApiError(String msg, HttpStatus status) {
        return ErrorResponse.builder()
                .status(status.value())
                .message(msg)
                .error(status.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> conflict(final WebRequest request, DuplicateException ex) {
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getMessage(),ex.getArgs()), HttpStatus.CONFLICT),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(final WebRequest request, NotFoundException ex) {
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getMessage(),ex.getArgs()), HttpStatus.NOT_FOUND),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> appException(final WebRequest request,
                                               AppException ex) {
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getErrorInfo().getErrorText(), ex.getErrorInfo().getArgs()), HttpStatus.INTERNAL_SERVER_ERROR),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authException(final WebRequest request,
                                                AuthenticationException ex) {
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getMessage(), ex.getArgs()), HttpStatus.UNAUTHORIZED),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> validationHandler(final WebRequest request, ValidationException ex) {
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getMessage(), ex.getArgs()), HttpStatus.BAD_REQUEST),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> invalidArguments(final WebRequest request, MethodArgumentNotValidException ex) {
        return handleSpringValidationException(request, ex.getBindingResult(), ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> invalidArguments(final WebRequest request, BindException ex) {
        return handleSpringValidationException(request, ex.getBindingResult(), ex);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(final WebRequest request, Exception ex) {
        log.info("Entering the default error handler");
        return handleExceptionInternal(ex, getApiError(getMsg(ex.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String getMsg(String code, Object[] args) {
        return messageSource.getMessage(code, args, code, null);
    }

    private String error(FieldError error) {
        return (error != null) ? error.getDefaultMessage() : "";
    }

    private ResponseEntity<Object> handleSpringValidationException(final WebRequest request, BindingResult result, Exception ex) {
        String msg = result.getFieldErrors()
                .stream()
                .map(this::error)
                .collect(Collectors.joining("\n"));

        return handleExceptionInternal(ex, getApiError(getMsg(msg, null), HttpStatus.BAD_REQUEST),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{} ", ExceptionUtils.getStackTrace(ex));

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        return new ResponseEntity(body, headers, status);
    }
}