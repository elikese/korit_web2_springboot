package com.koreait.spring_boot_study.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// filter -> 요청과 응답의 전처리 or 후처리를 위해 존재
@Component
@RequiredArgsConstructor // @AutoWired 자동화 - final 필드에 대해서
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 예비요청(Preflight) 패스 - Cors 에러 관련
        // 예비요청은 get, post... 실제요청 전에 항상 브라우저가 보내는 요청
        String requestMethod = request.getMethod();
        if(requestMethod.equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response); // 다음필터로 패스
            return;
        }

        // 요청에 담긴 jwt토큰 추출
        // 요청의 헤더에 존재(authorization key에 담아둠)
        String authHeader = request.getHeader("authorization");

        // "Bearer " 접두가 없다면 다음 필터로 넘긴다
        if(!jwtUtil.isBearer(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

    }
}
