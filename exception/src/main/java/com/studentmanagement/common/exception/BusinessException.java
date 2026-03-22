package com.studentmanagement.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    
    private final HttpStatus status;
    private final String error;

    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST, "Business Error");
    }

    public BusinessException(String message, HttpStatus status) {
        this(message, status, "Business Error");
    }

    public BusinessException(String message, HttpStatus status, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
}
