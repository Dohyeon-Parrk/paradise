package com.example.paradise.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDeleteRequest {
    @NotBlank
    private String password;
}
