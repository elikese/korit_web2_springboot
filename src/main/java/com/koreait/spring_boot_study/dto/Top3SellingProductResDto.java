package com.koreait.spring_boot_study.dto;

import com.koreait.spring_boot_study.model.Top3SellingProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Top3SellingProductResDto {
    private String productName;
    private int totalSoldCount;
    // List<모델> -> List<DTO> 변환 (서비스에서)
    // map 사용!

    // 모델 -> Dto 변환메서드
    public static Top3SellingProductResDto from(Top3SellingProduct model) {
        return new Top3SellingProductResDto(
                model.getProductName(),
                model.getTotalSoldCount()
                );
    }

}
