package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    // insert
    int insertRefreshToken(
            @Param("userId") int userId,
            @Param("token") String token,
            @Param("expireAt")LocalDateTime expireAt
            );

    // 토큰값으로 조회 - select
    Optional<RefreshToken> findByToken(@Param("token") String token);

    // 재발급 - update
    int updateRefreshToken(
            @Param("oldToken") String oldToken,
            @Param("newToken") String newToken
    );

    // 삭제 - delete

    int deleteByToken(@Param("token") String token);

    // refresh로 요청이 오면, 조회 후 새로 발급해줘야함
    // 이전것을 삭제해줘야함
    // -> refresh토큰의 수명이 길기 때문에 이전것을 탈취할 위험이 있기 때문
    int deleteAllByUserId(@Param("userId") int userId);
    
    // 만료기간이 지난 토큰데이터를 삭제
    // 현재 리프레쉬 토큰 만료시간이 1일
    // 로그인 후 2일 뒤에 다시 접속하면
    // auth/refresh 로 요청을 해도 리프레쉬 토큰이 만료되어서
    // access 토큰을 발급해 주지 x
    // 그럼, 진짜로 재로그인을 하게됨 -> refresh토큰이 새로 생성
    // -> 이전에 만료시간이 다 된 토큰은 여전히 db에 남아있게됨
    int deleteExpiredTokens();
}
