package com.example.paradise.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long userId;
    private Long postId;
    private String content;
}
