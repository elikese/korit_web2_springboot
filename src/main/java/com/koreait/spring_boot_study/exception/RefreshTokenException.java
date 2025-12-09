package com.koreait.spring_boot_study.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenException extends RuntimeException {
    private final HttpStatus status;

    public RefreshTokenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
