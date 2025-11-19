package com.koreait.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifyPostReqDto {
    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Size(max = 100, message = "제목은 100자 이상 불가능합니다.")
    private String title;

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Size(max = 1000, message = "내용은 1000자 이상 불가능합니다.")
    private String content;
}
