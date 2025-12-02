package com.koreait.spring_boot_study.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchPostResDto {
    private String title;
    private String content;
}
