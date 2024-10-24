package com.example.paradise.domain.comment.dto;

import com.example.paradise.domain.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String comments;
    private LocalDateTime createdAt;

    public CommentResponseDto(Long id, String content, String comments, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getPost().getContent(),
                comment.getComments(),
                comment.getCreatedAt()
        );
    }
}