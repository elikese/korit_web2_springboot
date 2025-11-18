package com.koreait.spring_boot_study.exception;

// 커스텀예외 - RuntimeException을 상속받으면 커스텀예외를 만들 수 있다.
// 게시글을 찾을 수 없을때(id 조회 or 이름 조회...)
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
