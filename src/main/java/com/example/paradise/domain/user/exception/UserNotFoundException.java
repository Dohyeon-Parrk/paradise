package com.example.paradise.domain.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("유저 정보를 찾을 수 없습니다: " + userId);
    }
}
