package com.koreait.spring_boot_study.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // @Data -> @ToString, @Getter, @Setter, @EqualAndHashCode
public class ModifyProductReqDto {
    @NotBlank(message = "상품이름은 비울 수 없습니다.")
    @Size(max = 50, message = "상품이름은 50자를 넘어갈 수 없습니다.")
    private String name;

    @Positive(message = "상품가격은 양수여야 합니다.")
    @Min(value = 1000, message = "상품은 최소 1000원부터 등록가능 합니다.")
    private int price;
}
