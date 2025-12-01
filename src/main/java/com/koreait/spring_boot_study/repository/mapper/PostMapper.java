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
}
