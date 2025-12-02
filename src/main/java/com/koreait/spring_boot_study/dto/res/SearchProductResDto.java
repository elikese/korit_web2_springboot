package com.koreait.spring_boot_study.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchProductResDto {
    private String productName;
    private int productPrice;
}
