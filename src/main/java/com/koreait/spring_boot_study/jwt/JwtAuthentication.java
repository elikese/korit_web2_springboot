package com.koreait.spring_boot_study.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

// 인증 성공시 만들어질 Authentication 객체
// filter에서 검증에 성공하면 Authentication 객체를 만들고
// 그 객체를 SecurityContext에 저장
// 다음 필터로 넘어갈때 SecurityContext에 Authentication객체가 있는지 확인하고
// 있고, 유효하면, 인증이 되어서 survlet으로 요청이 넘어간다

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private final String userId; // 유저 식별자
    private final List<GrantedAuthority> authorities; // 유저의 권한
    private boolean isAuthenticated = true; // 인증여부

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
        // ROLE_ADMIN or ROLE_USER 가 담겨있는걸 get 해간다.
        // 스프링 시큐리티는 이 getter로 유저의 role을 검사한다.
    }

    @Override
    public Object getCredentials() {
        return null; // 세션 인증용(아이디, 패스워드..)
    }

    @Override
    public Object getDetails() {
        return null; // 세션 정보용(ip주소..)
    }

    @Override
    public Object getPrincipal() { // 시큐리티에서 사용자 식별자를 Principal이라고 함
        return userId; // 사용자 식별자
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
        // true여야 SecurityContext에 저장된 Authentication 객체가
        // 인증에 성공한다.
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
        // false를 주입해서, 인증 취소할수있는 가능성을 열어둠
    }

    @Override
    public String getName() {
        return userId; // 인증에 대한 식별자 - 현재 우리의 구조에선 사용자 식별과 동일
    }
}
