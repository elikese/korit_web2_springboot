package com.koreait.spring_boot_study.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// filter -> 요청과 응답의 전처리 or 후처리를 위해 존재

@RequiredArgsConstructor // @AutoWired 자동화 - final 필드에 대해서
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // jwt 토큰 전략을 사용하기 때문에
    // 사실상 이 필터에서 인증이 일어나지 않으면,
    // 다른 어떤 필터들에서도 인증할 수 없는 구조.
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

        // "Bearer " 접두가 없다면 다음 필터로 넘긴다 -> 사실상 인증실패
        if(!jwtUtil.isBearer(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 추출완료
        String token = jwtUtil.removeBearer(authHeader);
        
        // 토큰 검증 -> 검증실패시 예외가 터짐
        try {
            // claims를(payload) 꺼낸다!(검증이 일어난다)
            // getClaims()에서 위조/만료시간 검증을 수행 -> 예외발생가능
            Claims claims = jwtUtil.getClaims(token);

            // claim : Map<String, Object>이다. 다운캐스팅 해줘야한다.
            String type = claims.get("type", String.class);
            // 꺼낸 타입이 ACCESS 토큰이 아니라면
            if(!type.equals("ACCESS")) {
                throw new JwtException("엑세스 토큰이 아닙니다.");
            }

            // Authentication을 만들어야 한다!
            // 만들려면 userId / authorities 필요
            String userId = claims.get("sub", String.class);
            String roleName = claims.get("role", String.class);
            // 스프링 시큐리티는 권한(role)검사할때 접두사("ROLE_")가 필수로 있어야 한다.
            GrantedAuthority jwtAuth = new SimpleGrantedAuthority("ROLE_" + roleName);
            List<GrantedAuthority> authorities = List.of(jwtAuth);

            // Authentication 완성
            JwtAuthentication authentication = new JwtAuthentication(userId, authorities);

            // SecurityContext(인증저장소)는 여러 사용자들의 인증정보 저장소
            // 함부로 접근하지 못하게 SecurityContextHolder라는 심부름꾼을 만들어놓았다.
            // 모든 필터들은 SecurityContext를 공유하고 있음.
            // 다음 필터에 넘어갈 시점에 authentication을 SecurityContext에 저장했다면
            // 인증이 완료 처리된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        } catch (JwtException e) {
            // 검증 실패시 예외가 발생할 수 있음
            // 우리가 예외를 구분해야한다.
            // 시간 만료시 -> ExpireJwtException(JwtException을 상속받고있음)
            // -> 특정 에러메세지를 프론트로 내려주고 재요청시킬거임(refresh 컨트롤러로)
            request.setAttribute("exception", e);
            // 예외가 전파되지 않게하고
            // 발생한 예외는 request 객체에 담아두자!
        }

        // 다음필터 실행
        filterChain.doFilter(request, response);
    }
}
