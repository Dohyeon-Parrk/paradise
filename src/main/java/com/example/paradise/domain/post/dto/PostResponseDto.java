package com.example.paradise.domain.post.dto;

import com.example.paradise.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public PostResponseDto(Long postId, String content, String username){
        this.postId = postId;
        this.content = content;
        this.username = username;
    }
}



