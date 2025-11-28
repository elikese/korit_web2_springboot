package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail {
    private int orderDetailId;
    private int orderId; // fk
    // private int productId; // fk
    private int quantity;

    // 연관관계를 가지려면, fk대신 객체를 가지고 있으면 된다.
    // fk(다른 테이블의 pk)를 칼럼으로 가지고 있다 ->
    // 1개의 product는 N개의 orderDetail을 가질 수 있다.
    private Product product;
}
