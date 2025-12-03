package com.koreait.spring_boot_study.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Post { // 테이블명:post -> 클래스명: Post
    private int id; // 컬럼명: post_id -> 필드명: postId
    private String title; // 컬럼명: post_title -> 필드명: postTitle
    private String content;// 컬럼명: post_content -> 필드명: postContent

    public Post(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    /*
        pk를 fk로 들고있는 쪽이 N이다.
        Post : Comment = 1:N
    */
    private List<Comment> comments;
}
