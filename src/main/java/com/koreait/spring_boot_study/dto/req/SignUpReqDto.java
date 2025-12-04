package com.koreait.spring_boot_study.dto.req;

import com.koreait.spring_boot_study.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpReqDto {
    private String username;
    private String password;
    private String name;
    private String email;

    // dto -> entity
    public User toEntity() {
        return User.builder() // password는 차후에 따로 set해준다.
                .username(this.username)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
