package com.example.paradise.user.controller;

import com.example.paradise.user.dto.UserLoginRequest;
import com.example.paradise.user.dto.UserPasswordUpdateRequest;
import com.example.paradise.user.dto.UserRegisterRequest;
import com.example.paradise.user.entity.User;
import com.example.paradise.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        User registeredUser = userService.registerUser(request);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginRequest request) {
        userService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("로그인 성공");
    }
    // 모든 회원 조회
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    // 특정 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    // 비밀번호 변경
    @PutMapping("/{id}")
    public  ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordUpdateRequest request) {
        User updatedUser = userService.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok(updatedUser);
    }
    // 회원 탈퇴
}
