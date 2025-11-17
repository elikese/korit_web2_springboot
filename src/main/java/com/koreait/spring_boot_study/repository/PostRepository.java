package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 전체게시글 조회 구현
    // 게시글 단건 조회 구현

}
