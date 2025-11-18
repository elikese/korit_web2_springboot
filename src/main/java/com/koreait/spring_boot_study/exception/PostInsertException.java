package com.koreait.spring_boot_study.exception;

public class PostInsertException extends RuntimeException {
    public PostInsertException(String message) {
        super(message);
    }
}
