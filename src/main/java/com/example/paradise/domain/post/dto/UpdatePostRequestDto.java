package com.example.paradise.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePostRequestDto {
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
}
