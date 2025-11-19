package com.koreait.spring_boot_study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // @Data -> @ToString, @Getter, @Setter, @EqualsAndHash
public class ModifyProductReqDto {
    private String name;
    private int price;
}
