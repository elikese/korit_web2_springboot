package com.koreait.spring_boot_study.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInReqDto {
    private String username;
    private String password;
}
