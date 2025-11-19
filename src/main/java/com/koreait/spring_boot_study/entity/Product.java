package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// entity -> 관계형 데이터베이스 테이블과 1:1 대응되는 되는 자바객체
@AllArgsConstructor
@Getter @Setter @ToString
public class Product {
    private int id;
    private String name;
    private int price;
}
