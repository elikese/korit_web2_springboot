package com.koreait.spring_boot_study.exception;

public class ProductInsertException extends RuntimeException {
    public ProductInsertException(String message) {
        super(message);
    }
}
