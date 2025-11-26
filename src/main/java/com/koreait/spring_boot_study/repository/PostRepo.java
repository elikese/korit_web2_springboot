package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepo {

    // 전체게시글 조회 구현, 글제목 조회
    List<Post> findAllPosts();
    // 게시글 단건 조회 구현, 글제목 조회
    Optional<Post> findPostById(int id);
    // 단건 추가
    int insertPost(String title, String content);
    // 단건 삭제 by id
    int deletePostById(int id);
    // 단건 업데이트 by id and entity
    int updatePost(int id, String title, String content);
}
