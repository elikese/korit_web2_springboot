package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Role {
    private int roleId;
    private String roleName;
    private LocalDateTime createAt;// mysql DATETIME에 대응되는 자바 자료형
    private LocalDateTime updateAt;
}
