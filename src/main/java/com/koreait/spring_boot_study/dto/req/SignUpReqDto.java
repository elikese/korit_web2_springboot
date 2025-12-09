package com.koreait.spring_boot_study.dto.req;

import com.koreait.spring_boot_study.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpReqDto {
    /*
    정규식(regex) - 프로그래밍언어로 입력데이터 패턴을 지정하는 방식
    [a-z]   # 영문 소문자 a~z 중 1개
    [A-Z]   # 영문 대문자 A~Z 중 1개
    [0~9]   # 숫자 0~9 중 1개
    [a-zA-Z] # 영문 대소문자 중 1개
    {4,20} # 4개 이상 20개 이하
    {4,} # 4개 이상 ^[a-z]{4,}$ : 영문 소문자 4개이상
    {4} # 정확히 4개
    ^ 정규식 시작
    $ 정규식 끝
    ?=.*[패턴] : 해당패턴을 하나 이상 포함
    ^?=.*[A-Za-z]{4,10}$ : 영대소문자를 하나이상포함하는 4~10자
    */

    // validation은 간략하게
    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(
    regexp = "^[a-z0-9]{4,20}$",
    message = "아이디는 4~20자의 영문 소문자, 숫자만 사용 가능합니다."
    )
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
