package com.example.paradise.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
