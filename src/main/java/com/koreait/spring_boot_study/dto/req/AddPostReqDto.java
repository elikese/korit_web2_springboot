package com.koreait.spring_boot_study.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AddPostReqDto {

    @NotBlank(message = "제목은 비울 수 없습니다.")
    @Size(max=100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비울 수 없습니다.")
    @Size(max=5000, message = "내용은 5000자를 초과할 수 없습니다.")
    private String content;
}
