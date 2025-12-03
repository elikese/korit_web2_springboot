package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.req.AddPostReqDto;
import com.koreait.spring_boot_study.dto.req.ModifyPostReqDto;
import com.koreait.spring_boot_study.dto.req.SearchPostReqDto;
import com.koreait.spring_boot_study.dto.res.PostResDto;
import com.koreait.spring_boot_study.dto.res.PostWithCommentsResDto;
import com.koreait.spring_boot_study.dto.res.SearchPostResDto;
import com.koreait.spring_boot_study.dto.res.SearchProductResDto;
import com.koreait.spring_boot_study.entity.Post;
import com.koreait.spring_boot_study.exception.PostInsertException;
import com.koreait.spring_boot_study.exception.PostNotFoundException;
import com.koreait.spring_boot_study.repository.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostMapper postRepository;

    @Autowired
    public PostService(PostMapper postRepository) {
        this.postRepository = postRepository;
    }

    // 전체 글제목 조회
    public List<String> getAllPostNames() {
        return postRepository.findAllPosts() // List<Post>
                .stream()
                .map(post -> post.getTitle()) // Stream<String>
                .collect(Collectors.toList());
    }

    // 게시글 단건조회 : isEmpty -> 정석) 예외를 던져야함(커스텀예외)
    public String getPostTitleById(int id) {
        Optional<Post> postOptional = postRepository.findPostById(id);
        // 옵셔널을 언패킹하는 다른 방법(예외도 같이 던질 수 있음)
        // 옵셔널.orElseThrow() : 
        // Optional에 포장된 객체가 null이 아니면 post 변수에 담고,
        // null이면 예외를 던지세요
        Post post = postOptional.orElseThrow(
                () -> new PostNotFoundException("게시글을 찾을 수 없습니다")
        );
        String title = post.getTitle();
        return title;
    }

    // 게시글 전체 리턴
    public List<PostResDto> getAllPost() {
        return postRepository.findAllPosts() // List<Post>
                .stream()
                .map(post
                        -> new PostResDto(post.getTitle()
                        , post.getContent())) // Stream<PostResDto>
                .collect(Collectors.toList()); // List<PostResDto>
    }

    // 게시글 단건 조회
    public PostResDto getPostById(int id) {
        Post post = postRepository.findPostById(id) // Optional<Post>
                .orElseThrow(
                        () -> new PostNotFoundException("게시글을 찾을 수 없습니다.")
                );
        return new PostResDto(post.getTitle(), post.getContent());
    }

    public void addPost(AddPostReqDto dto) {
        int successCount = postRepository
                .insertPost(dto.getTitle(), dto.getContent());

        if(successCount <= 0) {
            throw new PostInsertException("게시글 등록 중 에러가 발생했습니다.");
        }
    }

    // 게시글 단건 삭제
    public void removePost(int id) {
        int successCount = postRepository.deletePostById(id);
        
        if(successCount <= 0) { // 성공건수가 0이라면
            throw new PostNotFoundException("게시글을 찾을 수 없습니다.");
        }
    }

    // 게시글 단건 업데이트
    public void modifyPost(int id, ModifyPostReqDto dto) {
        int successCount = postRepository
                .updatePost(id, dto.getTitle(), dto.getContent());
        if(successCount <= 0) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }
    }

    // 게시물 상세 검색 dto - req, res 각각 만들어 주는게 best
    public List<SearchPostResDto> searchDetailPosts(
            SearchPostReqDto dto
    ) {
        List<Post> posts = postRepository.searchDetailPosts(
                dto.getTitleKeyword(), dto.getContentKeyword()
        );

        if(posts == null || posts.isEmpty()) {
            throw new PostNotFoundException("조건에 맞는 게시글이 없습니다.");
        }

        // List<Post> -> List<SearchPostResDto>
        // List<entity> -> List<dto>
        return posts.stream()
                .map(p -> new SearchPostResDto(p.getTitle(), p.getContent()))
                .collect(Collectors.toList());
    }

    public PostWithCommentsResDto getPostWithComments(int id) {
        Post post = postRepository.findPostWithComments(id)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));

        // comments null 체크해야함!
        List<String> comments
                = post.getComments() == null
                ? List.of() // null이면 빈리스트 반환
                : post.getComments().stream()
                .map(c -> c.getCommentContent())
                .collect(Collectors.toList()); // 아니라면, id빼고 content 내용만

        return new PostWithCommentsResDto(
                post.getTitle(),
                post.getContent(),
                comments
        );
    }

    public void addPosts(List<AddPostReqDto> dtoList) {
        // List<dto> -> List<entity>

        List<Post> entityList = new ArrayList<>(); // 빈 리스트
        // 매개변수로 들어온 dtoList를 순회
        for(AddPostReqDto dto : dtoList) {
            // dto 필드에 있는 걸 entity 필드로 대입시켜 초기화
            Post post = Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .build();

            entityList.add(post);
        }
        int successCount = postRepository.insertPosts(entityList);

        if(successCount != entityList.size()) {
            throw new PostInsertException("게시글 등록 중 문제가 발생했습니다.");
        }
    }


}
