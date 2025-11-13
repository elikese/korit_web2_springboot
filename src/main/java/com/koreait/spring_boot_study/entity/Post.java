package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
public class Post {
    private int id;
    private String title;
    private String content;
}
