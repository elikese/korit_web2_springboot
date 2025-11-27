package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
public class Post { // 테이블명:post -> 클래스명: Post
    private int id; // 컬럼명: post_id -> 필드명: postId
    private String title; // 컬럼명: post_title -> 필드명: postTitle
    private String content;// 컬럼명: post_content -> 필드명: postContent
}
