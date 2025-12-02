package com.koreait.spring_boot_study.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostWithCommentsResDto {
    private String postTitle;
    private String postContent;
    private List<String> comments;
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
}
