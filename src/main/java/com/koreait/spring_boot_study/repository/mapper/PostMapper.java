package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    // 전체게시글 조회 구현, 글제목 조회
    List<Post> findAllPosts();
    // 게시글 단건 조회 구현, 글제목 조회
    Optional<Post> findPostById(int id);
    // 단건 추가
    int insertPost(
            @Param("title") String title,
            @Param("content") String content
    );

    // Controller는 구체적인 Service 클래스를 알 필요가 없다.
    // Controller는 Service의 메서드 시그니처들만 알면 된다.(넘겨줄 매개변수, 받을 리턴값)

    // Service는 구체적인 Repository 클래스를 알 필요가 없다.
    // Service는 Repository의 메서드 시그니처들만 알면 된다.(넘겨줄 매개변수, 받을 리턴값)

    // controller <interface> service <interface> repository : 가장 이상적

    // 단건 삭제 by id
    int deletePostById(int id);
    // 단건 업데이트 by id and entity
    int updatePost(
            @Param("id") int id,
            @Param("title") String title,
            @Param("content") String content
    );
    
    // 1. titleKeyword 혹은 contentKeyword로 post를 상세검색하는
    // xml, mapper, service, controller 작성
    List<Post> searchDetailPosts(
            @Param("titleKeyword") String titleKeyword,
            @Param("contentKeyword") String contentKeyword
    );

    // 2. Post + Comment 조인 단건 조회 by Id
    /*
    최종 결과
    {
        postTitle: ~
        postContent: ~
        comments: [
            '댓글1',
            '댓글2',
            '댓글3'
        ]
    }
    */

    Optional<Post> findPostWithComments(int id);

    // post 다건입력 xml, service, controller
    int insertPosts(List<Post> posts);
}
