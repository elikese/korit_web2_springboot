package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor // 모든 필드를 초기화하는 생성자 코드 작성됨
@Getter // 모든 필드의 getter 코드 작성됨
@ToString
public class Student {
    private int id;
    private String name;
    /*
    {
        "id" : 1,
        "name" : "홍길동"
    }
    */
}
