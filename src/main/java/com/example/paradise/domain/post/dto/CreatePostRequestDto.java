package com.example.paradise.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePostRequestDto {
//    @NotNull(message = "userId는 필수 항목입니다")
//    private Long userId;
    @NotNull(message = "content은 필수 항목입니다")
    private String content;
}
