package com.koreait.spring_boot_study.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchProductReqDto {
    private String nameKeyword;
    private Integer minPrice;
    private Integer maxPrice;
}
