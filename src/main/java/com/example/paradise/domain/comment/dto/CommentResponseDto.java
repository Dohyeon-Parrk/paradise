package com.example.paradise.domain.comment.dto;

import com.example.paradise.domain.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Long id, Long userId, String username, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }
}
