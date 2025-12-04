package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController { // 회원가입, 로그인, 로그아웃
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody @Valid SignUpReqDto dto) {

        authService.signUp(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("계정생성 완료");
    }
}
