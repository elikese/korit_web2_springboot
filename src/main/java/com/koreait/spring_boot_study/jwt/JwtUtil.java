package com.koreait.spring_boot_study.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;

@Component
public class JwtUtil { // jwt 토큰 발급 & jwt 토큰 검증
    private final SecretKey key;
    private final long accessExpireMillis;
    private final long refreshExpireMillis;

    public JwtUtil(
            // @Value로 yaml에 있는 민감한 정보를 참조할 수 있음
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expire-millis}") long accessExpireMillis,
            @Value("${jwt.refresh-expire-millis}") long refreshExpireMillis
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpireMillis = accessExpireMillis;
        this.refreshExpireMillis = refreshExpireMillis;
    }
    
    
    /*
        jwt토큰 -> 암호화된 문자열
        jwt토큰의 구조: [head].[payload-claim들이 저장됨].[signature]
        head: 토큰의 메타데이터
        payload: key-value 형태로 데이터를 저장할 수 있음(유저 식별, 생성시간, 만료..)
        signature: 위조여부를 검사하는 검사지
    */
    // ################ 토큰 발급 ################ : 로그인(signIn)시 발급!
    private String buildToken(
            String subject, // 유저 식별
            long expireMills, // 만료시간
            Map<String, Object> extraClaims, // payload에 담을 데이터
            String type // access, refresh 구분용
    ) {
        long now = System.currentTimeMillis(); // 현재시간을 long으로 가져옴
        long exp = now + expireMills; // 만료시'각'

        // 토큰의 payload에 claim들을 담아줍니다
        JwtBuilder builder = Jwts.builder()
                .claim("sub", subject) // 유저식별
                .claim("iat", now / 1000) // issuedAt 발급시각
                .claim("exp", exp / 1000) // expireAt 만료시각
                .claim("type", type); // ACCESS or REFRESH

        // 만약 추가 claims(Map)가 있다면
        if(extraClaims != null) {
            // map을 순회하면서, builder.claim(key, value)를 실행
            extraClaims.forEach((k, v) -> builder.claim(k, v));
        }

        return builder
                .signWith(key) // secret을 사용해서 암호화
                .compact();
    }

    // accessToken 발급
    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return buildToken(subject, accessExpireMillis, claims, "ACCESS");
    }

    // refreshToken 발급
    // refreshToken -> accessToken이 만료되면 다시 accessToken을 발행하도록
    // 이전에 인증이 되었다고 증명해주는 토큰
    public String generateRefreshToken(String subject) {
        return buildToken(subject, refreshExpireMillis, Map.of(), "REFRESH");
    }

    // ################ 토큰 검증 ################ : PermitAll()이 적용되지않은 모든 컨트롤러 주소

    // claim들 추출 & 토큰 검증
    public Claims getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(key) // 복호화
                .build()
                .parseSignedClaims(token)// 여기서 실제 검증이 이루어짐
                // 1. signature 검증 (위조여부 검사) -> 검증실패시 예외 던져짐
                // 2. 만료시각 검증 -> 검증실패시 예외 던져짐
                .getPayload();
    }

    // Jwt토큰(문자열)을 사용자에게 발급한다
    // 사용자는 그 이후로부터 요청할때 요청 헤더에
    // Authorization : "Bearer " + 토큰문자열
    // 첨부하여 요청을 서버로 보내야 한다.
    public boolean isBearer(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    public String removeBearer(String header) {
        // "Bearer " 접두를 떼준다.
        return header.substring("Bearer ".length());
    }


}
