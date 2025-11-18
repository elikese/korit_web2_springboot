package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    // 생성될때 값이 정해지고, 불변
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 전체 게시글의 제목 조회
    @GetMapping("/title/all")
    public ResponseEntity<?> getAllPostTitles() {
        List<String> posts = postService.getAllPostNames();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/title/{id}")
    public ResponseEntity<?> getPostTitleById(@PathVariable int id) {
        String title = postService.getPostTitleById(id);
        return ResponseEntity.ok(title);
    }

}
