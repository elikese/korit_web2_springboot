package com.koreait.spring_boot_study.entity;

public class Comment {
    private int commentId;
    private String commentContent;

    // private int PostId;
    private Post post; // fk대신에 객체를 필드로 가지고 있어야한다.
    // comment.getPost().getId() -> 계속된 참조로 탐색하는것(객체 그래프탐색)

    // 하나의 글에는 여러개의 댓글이 있다.
    // -> 1개의 Post는 여러개의 Comment 가질수 있다.
    // 1. fk 누가 가지고 있어야하나? Comment가 PostId(fk)가지고 있어야한다.
    // 2. 하나의 댓글은 하나의 글에만 달릴수 있다.
    // -> Post : Comment = 1:N

    /*
    테이블 설계 노하우
    fk를 설정하는 순간 -> 1:N(fk를 가진테이블) 관계를 만들겠다는 것
    N:M 관계도 설정 가능함
    학생 : 강의 -> 학생이 강의 fk, 강의도 학생 fk (N:M 관계)
    "등록" 테이블을 생성(학생 fk, 강의 fk)
    학생: 등록 (1: N)
    강의: 등록 (1: M)
    -> 이론적으로 모든 테이블을 1:N 관계로 설정가능하다!
    */

}
