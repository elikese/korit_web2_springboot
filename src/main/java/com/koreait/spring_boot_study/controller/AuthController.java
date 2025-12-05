package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.SignInReqDto;
import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.dto.res.SignInResDto;
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

    // 논리적으로 GetMapping이 맞으나(조회), param등에 민감정보가 노출
    // 민감한정보를 주고 받아야한다 -> body가 필요함 -> PostMapping
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @RequestBody SignInReqDto reqDto) {
        SignInResDto resDto = authService.signIn(reqDto);

        // refreshToken은 cookie(헤더)에 담아서 응답 (나중에)

        // body로 accessToken만 응답해준다.
        return ResponseEntity.ok(resDto.getAccessToken());
    }


}
