package com.koreait.spring_boot_study.config;

import com.koreait.spring_boot_study.jwt.JwtAuthenticationEntryPoint;
import com.koreait.spring_boot_study.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // 사용자 비밀번호를 암호화하는 객체(시큐리티 라이브러리)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Component를 정의해서, 컴포넌트스캔을 사용해도 된다.
    // 시큐리티 설정에 관여하므로, 명시적으로 bean 등록을 하는걸 권장
    @Bean // 인증실패시 실패응답을 처리할 entryPoint
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    /*
    CORS(Cross-Origin Resource Sharing) 설정 
    - CORS 에러를 방지하기 위해 설정
    브라우저(크롬, 웨일..)에서 다른 출처(origin)로 요청을 보낼 때, 
    브라우저정책 때문에 요청을 막아서 발생하는 에러
    -> origin이 다르면 에러가 발생
    *origin) 프로토콜(http, https), 도메인(localhost, naver.com), 포트(3000, 8080)
    위 3가지 요소가 모두 일치해야 같은 origin
    
    전통웹(SSR) 방식에서는 http:localhost:8080/page.html -> 사용자화면
    http:localhost:8080/main.js 주세요 요청 가능
    http:localhost:8080/img.png 주세요 요청 가능
    http:localhost:5173(브라우저화면) 에서 localhost:8080으로 요청은 불가능(origin이 다름)
    -> 이때 브라우저는 이 상황을 '보안상 위험'하다고 판단 -> CORS 에러
    결론: 서버(localhost:8080)에서 우리에게 보내는 요청은 안전합니다 라는 설정을 해줘야한다.
    */
    @Bean 
    public CorsConfigurationSource corsConfigurationSource() {
        
        CorsConfiguration cors = new CorsConfiguration(); // 설정객체
        // #### 쿠키 관련 설정 ####
        // 요청을 보내는 쪽의 도메인(naver.com) 모두 허용
        // cors.addAllowedOriginPattern(CorsConfiguration.ALL);
        // 1. 쿠키를 사용하려면, 특정 도메인을 지정해 줘야함.
        cors.setAllowedOrigins(List.of(
                "http://localhost:3000"
        )); // 특정 origin만 허용해야 쿠키 사용 가능

        cors.setAllowCredentials(true); // 2. 쿠키를 쓰겠습니까?
        cors.setExposedHeaders(List.of( // 3. 헤더에 쿠키를 담은것을 명시
                "Set-Cookie"
        ));


        // 요청을 보내는 쪽의 Req, Res 헤더 정보에 대한 제한 모두 허용
        cors.addAllowedHeader(CorsConfiguration.ALL);
        // 요청 보내는 쪽의 메서드(get, post...) 모두 허용
        cors.addAllowedMethod(CorsConfiguration.ALL);

        // 요청 url에 대한 cors 설정을 적용하기 위한 객체(배달부)
        UrlBasedCorsConfigurationSource sc
                = new UrlBasedCorsConfigurationSource();

        // /**: 모든 url 패턴
        sc.registerCorsConfiguration("/**", cors);
        return sc;
    }

    // filterChain 설정 (filter 설정)
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        // 위에서 Bean으로 만든 설정객체를 security에 적용
        http.cors(Customizer.withDefaults());
        // 세션기반 기능들 off
        // csrf : 세션기반 공격(사용자의 세션을 탈취해서 공격)
        http.csrf(csrf -> csrf.disable());
        // 폼 로그인(서버사이드 렌더링 방식)
        http.formLogin(form -> form.disable());
        // 세션기반 로그인방식
        http.httpBasic(basic -> basic.disable());
        // 세션 로그아웃
        http.logout(lg -> lg.disable());
        // 세션을 무상태 방식으로 변경(jwt 토큰방식)
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        
        // jwt 관련 필터 설정(나중에)
        // 1. jwt 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        // 2. jwt 인증실패 시 처리(entryPoint)
        http.exceptionHandling( e ->
            e.authenticationEntryPoint(jwtAuthenticationEntryPoint()));
        
        // url 요청에 대한 권한 설정
        http.authorizeHttpRequests(auth -> {
            // 특정 요청에 대해서는 검사하지 않고 통과
            auth.requestMatchers("/auth/**").permitAll();
            // 그 외 모든 경로에 대해서는 검사하겠다.
            auth.anyRequest().authenticated();

            // 우선 모두 통과
            // auth.anyRequest().permitAll();
        });
        
        return http.build();











    }
}
