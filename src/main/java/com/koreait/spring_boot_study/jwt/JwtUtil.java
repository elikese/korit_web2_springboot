package com.koreait.spring_boot_study.jwt;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil { // jwt 토큰 발급 & jwt 토큰 검증
    private final SecretKey key;
    private final long accessExpireMillis;
    private final long refreshExpireMillis;


}
