package com.koreait.spring_boot_study.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AddProductReqDto {

    // 검증 실패시! -> 예외를 던진다
    // 예외에 들어갈 메세지 -> message 속성
    @NotBlank(message = "이름은 비울 수 없습니다.")
    @Size(max= 50, message = "50글자 이상은 지을 수 없습니다.")
    private String name;

    @Positive(message = "가격은 양수여야 합니다.")
    private int price;
    /*
    스프링 Validation 라이브러리 사용법
    1. 문자열
    @NotBlank : null, "", "    " 모두 허용 안하겠다.
    @NotEmpty : null, "", 허용 안하겠다.
    @Size(min=, max=) 문자열 길이 제한
    @Email 이메일 형식 검사(정규식기반..)
    2. 숫자
    @Min(value=) 최소값 지정
    @Max(value=) 최대값 지정 
    @Positive 양수만 허용
    @Negative 음수만 허용
    3. 객체
    @Valid // 내부객체를 검증하라
    @NotNull // null만 금지
    */

}
