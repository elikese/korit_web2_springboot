package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.SignInReqDto;
import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.dto.res.SignInResDto;
import com.koreait.spring_boot_study.exception.RefreshTokenException;
import com.koreait.spring_boot_study.jwt.JwtUtil;
import com.koreait.spring_boot_study.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController { // 회원가입, 로그인, 로그아웃
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-expire-millis}")
    private long refreshExpireMillis;

    // 쿠키 - 특정 서버에서 쿠키를 내려주면
    // 앞으로 모든 클라이언트 - 서버 http 교신에서
    // 헤더에 쿠키를 담고 있게 된다.
    // 리프레쉬 토큰(쿠키에 담아서 응답)
    // 쿠키를 응답 헤더에 담는 메서드
    private void addRefreshTokenCookie(
            String refreshToken,
            HttpServletResponse response
    ) {
        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(false) // 실제운영시 true: JS 조작못하게 만든다
                .secure(false) // 실제운영시 true: https 프로토콜만 허용
                .sameSite("Lax") // csrf 정책 - get 요청은 허용
                .path("/")
                .maxAge(-1) // 쿠키의 유효기간 -1: 탭종료시 쿠키도 삭제
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

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
            @RequestBody SignInReqDto reqDto,
            // 컨트롤러: servlet Dispatcher가 일을 시키는 구조
            // servlet Dispatcher가 request, response 객체 가지고 있다.
            HttpServletResponse response
            ) {
        SignInResDto resDto = authService.signIn(reqDto);

        // refreshToken은 cookie(헤더)에 담아서 응답
        addRefreshTokenCookie(resDto.getRefreshToken(), response);

        // body로 accessToken만 응답해준다.
        return ResponseEntity.ok(resDto.getAccessToken());
    }

    /*
    accessToken이 만료되면, entryPoint에서 "error":"ACCESS_TOKEN_EXPIRED"
    라는 에러메세지를 응답한다. 프론트엔드에서 이 응답을 받으면, 자동으로
    /auth/refresh로 요청하게끔 설계한다
    */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            HttpServletResponse response,
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        // 쿠키에서 refresh토큰을 꺼내와야한다.
        if(refreshToken == null) {
            throw new RefreshTokenException(
                    "리프레시 토큰이 존재하지 않습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }
        // 서비스로 쿠키값(refresh 토큰) 넘긴다.
        SignInResDto resDto = authService.refreshToken(refreshToken);

        // 쿠키에 새로운 리프레쉬토큰 설정
        addRefreshTokenCookie(resDto.getRefreshToken(), response);
        // 응답 body에는 accessToken만 응답
        return ResponseEntity.ok(resDto.getAccessToken());
    }
    
    // 로그아웃
    // 프론트엔드에서 사실상 저장해뒀던 accessToken을 지워버리면 로그아웃이 구현된 것
    // 놀이공원 다 즐기고 나갈때 팔찌를 가위로 자르는것과 같음
    // 하지만 refreshToken은 db에 저장되어 있어서 누적되면 곤란하니 삭제해준다.

    public ResponseEntity<?> logout(
            @CookieValue(
                    value = "refreshToken", required = false
            ) String refreshToken,
            HttpServletResponse response
    ) {
        if(refreshToken == null) {
            throw new RefreshTokenException(
                    "리프레시 토큰이 존재하지 않습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 서비스 호출해서 db에서 refreshToken 삭제
        authService.logout(refreshToken);

        // 쿠키도 브라우저에서 제거
        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", "") // 토큰내용 빈값으로 교체
                .httpOnly(false) // 운영시 true
                .secure(false) // 운영시 true
                .sameSite("Lax") // 브라우저 get요청은 ok
                .path("/") 
                .maxAge(0) // 쿠키 수명을 0초 -> 삭제
                .build();
        
        // 응답헤더에 쿠키 적용
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("로그아웃 완료");
    }


}
