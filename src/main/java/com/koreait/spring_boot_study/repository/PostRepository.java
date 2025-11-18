package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    // CRUD(생성, 조회, 수정, 삭제)
    // DB 대용 필드 - sql쿼리로 DB에서 데이터를 받아옴(주로 List로)
    private List<Post> posts = new ArrayList<>(
            Arrays.asList(
                    new Post(1, "페이커 vs 손흥민", "누가 이김?"),
                    new Post(2, "박지성 vs 손흥민", "누가 이김?"),
                    new Post(3, "피카츄 vs 라이츄", "누가 이김?"),
                    new Post(4, "스프링부트 공부중", "반복 ㄱㄱ")
            )
    );

    // 전체게시글 조회 구현, 글제목 조회
    public List<Post> findAllPosts() {
        return posts;
    }

    // 게시글 단건 조회 구현, 글제목 조회
    public Optional<Post> findPostById(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst(); 
        // 객체가 있으면 객체를 Optional로 감싸서 리턴
        // 없으면 null을 Optional로 감싸서 리턴
    }

    // 단건 추가
    public int insertPost(String title, String content) {
        // maxId
        int maxId = 0;
        for(Post post: posts) {
            if(post.getId() > maxId) {
                maxId = post.getId();
            }
        } // auto_increment 기능
        Post post = new Post(maxId + 1, title, content);
        posts.add(post); // sql insert 쿼리
        
        return 1;
    }



}
