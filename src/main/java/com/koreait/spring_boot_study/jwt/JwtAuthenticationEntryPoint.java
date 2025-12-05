package com.koreait.spring_boot_study.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
    2가지 경우에 entrypoint로 요청이 들어오게 된다.
    1. 인증 예외 발생했는데, 전파가 된 경우(catch 안한경우)
    2. 필터체인이 끝났는데, 아직도 Authentication 객체가 없는경우
    but, PermitAll() 대상은 entryPoint로 안오고, servlet으로 간다.
    */

    public static final String EXPIRED_ERROR_MSG = """
            {
                "error": "ACCESS_TOKEN_EXPIRED"
            }
            """;

    public static final String INVALID_ERROR_MSG = """
            {
                "error": "INVALID_ACCESS_TOKEN"
            }
            """;

    public static final String UNAUTHORIZED_MSG = """
            {
                "error": "UNAUTHORIZED"
            }
            """;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 이전 filter에서 catch해서 request에 담아둔 예외객체 언박싱
        Exception e = (Exception) request.getAttribute("exception");

        // 컨트롤러 안가고, 여기서 응답을 내려줄거임
        // 응답헤더 설정
        // 응답바디를 json으로
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401); // 401




        // 만료예외는 따로 분기해서 처리!
        if(e instanceof ExpiredJwtException) {
            response.getWriter().write(EXPIRED_ERROR_MSG);
            return;
        }

        // 그외 JwtException 처리
        if (e instanceof JwtException) {
            response.getWriter().write(INVALID_ERROR_MSG);
            return;
        }

        // jwt 이외의 인증예외 처리
        response.getWriter().write(UNAUTHORIZED_MSG);
    }
}
