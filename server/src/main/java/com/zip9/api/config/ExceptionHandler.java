package com.zip9.api.config;

import com.zip9.api.common.dto.ErrorResponse;
import com.zip9.api.common.enums.Code;
import com.zip9.api.common.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, Code.VALIDATION_ERROR, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getCode(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(BeanInstantiationException e, WebRequest request) {
        return handleExceptionInternal(new GeneralException(e.getCause().getMessage()), Code.VALIDATION_ERROR, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(IllegalArgumentException e, WebRequest request) {
        return handleExceptionInternal(e, Code.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, Code.INTERNAL_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = String.format("%s (%s)", e.getFieldError().getDefaultMessage(), e.getFieldError().getField());
        return handleExceptionInternal(new GeneralException(message), Code.VALIDATION_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(e, Code.valueOf(status), headers, Code.valueOf(status).getHttpStatus(), request);
    }



    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code code, WebRequest request) {
        return handleExceptionInternal(e, code, HttpHeaders.EMPTY, code.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code code, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                ErrorResponse.of(code, code.getMessage(e)),
                headers,
                status,
                request
        );
    }

}
