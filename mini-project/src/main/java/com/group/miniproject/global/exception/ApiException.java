package com.group.miniproject.global.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ExceptionCode code;

    public ApiException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApiException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }
}
