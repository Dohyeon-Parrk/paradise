package com.example.paradise.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequest {
    // 유저명
    @NotBlank
    private String username;
    // 이메일(계정명)
    @Email
    @NotBlank
    private String email;
    // 비밀번호
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 하며, 최소 8자 이상이어야 합니다.") // 정규 표현식 이용
    private String password;
    // 비밀번호 확인
    @NotBlank
    private String confirmPassword;

    private boolean admin = false;
    private String adminToken = "";
}
