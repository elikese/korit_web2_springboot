package com.koreait.spring_boot_study.dto.req;

import com.koreait.spring_boot_study.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpReqDto {
    // validation은 간략하게
    @NotBlank(message = "아이디를 입력해주세요")
    private String username;

    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
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
