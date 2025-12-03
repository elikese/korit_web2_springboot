package com.koreait.spring_boot_study.entity;

import lombok.*;
import org.springframework.core.annotation.Order;

import java.util.List;

// entity -> 관계형 데이터베이스 테이블과 1:1 대응되는 되는 자바객체
@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Product {
    private int id;
    private String name;
    private int price;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // 하나의 Product는 여러번 주문될수 있다.
    // -> 하나의 Product는 여러개의 orderDetail을 가지고 있다.
    private List<OrderDetail> orderDetails;
    // 하나의 product_id로 여러 줄의 orderDetail 결과를 받을 수 있다.
    // -> 하나의 Product 객체가 여러개의 orderDetail 객체를 가질 수 있다.
}
