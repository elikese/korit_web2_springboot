package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.AddPostReqDto;
import com.koreait.spring_boot_study.dto.req.ModifyPostReqDto;
import com.koreait.spring_boot_study.dto.req.SearchPostReqDto;
import com.koreait.spring_boot_study.dto.res.PostResDto;
import com.koreait.spring_boot_study.dto.res.PostWithCommentsResDto;
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
            @Valid @RequestBody AddPostReqDto dto //잭슨이 dto를 만들 때, NoArgsConstructor필요!
    ) {
        postService.addPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("등록성공");
    }

    // 1. id를 받아서 -> 게시글을 삭제하는 컨트롤러 -> 서비스 -> 레파지토리
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePost(@PathVariable int id) {
        postService.removePost(id);
        return ResponseEntity.ok("삭제완료");
    }

    // 2. id와 dto를 받아서 -> 게시글을 업데이트하는 컨트롤러 -> 서비스 -> 레파지토리
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyPost(
            @PathVariable int id,
            @Valid @RequestBody ModifyPostReqDto dto
            ) {
        postService.modifyPost(id, dto);
        return ResponseEntity.ok("수정완료");
    }

    // 수정요청 PUT, PATCH
    // PUT -> 전체 데이터를 갈아끼우겠다(title, content)
    // PATCH -> 일부 데이터를 갈아끼우겠다(content)
    // -> null을 허용해야하는 경우가 많음

    // 게시물 조건 상세 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchDetailPosts(
            // RequestParam을 지정하면, 반드시 값이 있어야 한다.
            // 없으면 400 에러를 응답
            // required 옵션에 false를 지정하면, 옵션처럼 사용가능
            @RequestParam(required = false) String titleKeyword,
            @RequestParam(required = false) String contentKeyword
    ) {
        SearchPostReqDto dto
                = new SearchPostReqDto(titleKeyword, contentKeyword);

        return ResponseEntity.ok(postService.searchDetailPosts(dto));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getPostWithComments(@PathVariable int id) {
        PostWithCommentsResDto dto = postService.getPostWithComments(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add/bulk")
    public ResponseEntity<?> addPosts(
            @Valid @RequestBody List<AddPostReqDto> dtoList) {
        postService.addPosts(dtoList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("전체 게시글 등록 성공 : " + dtoList.size() + "건");
    }

}
