package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddPostReqDto;
import com.koreait.spring_boot_study.dto.PostResDto;
import com.koreait.spring_boot_study.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // localhost:8080/post/all - GET
    @GetMapping("/all")
    public ResponseEntity<?> getAllPost() {
        List<PostResDto> dtos = postService.getAllPost();
        return ResponseEntity.ok(dtos);
    }

    // localhost:8080/post/2 - GET (2번 게시글 조회)
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        PostResDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto);
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

    // 3. 단건 추가 컨트롤러 -> 서비스 -> 레파지토리 코드 작성
    // + Validation을 사용해봅시다!

    @PostMapping("/add")
    public ResponseEntity<?> addPost(
            @Valid @RequestBody AddPostReqDto dto
    ) {
        postService.addPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("등록성공");
    }

    // 4. 게시글 title, content를 담은 List 응답


}
