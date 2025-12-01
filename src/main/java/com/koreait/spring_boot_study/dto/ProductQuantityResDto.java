package com.koreait.spring_boot_study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQuantityResDto {
    private String productName;
    private int productPrice;
    private int quantity;
}
