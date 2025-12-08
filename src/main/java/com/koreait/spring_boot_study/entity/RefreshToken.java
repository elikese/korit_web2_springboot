package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshToken {
    private int refreshTokenId;
    private int userId;
    private String token;
    private LocalDateTime expireAt;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
