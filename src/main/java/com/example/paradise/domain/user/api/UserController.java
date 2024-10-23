package com.example.paradise.domain.user.api;

import com.example.paradise.domain.user.application.UserService;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.dto.UserDeleteRequest;
import com.example.paradise.domain.user.dto.UserLoginRequest;
import com.example.paradise.domain.user.dto.UserPasswordUpdateRequest;
import com.example.paradise.domain.user.dto.UserRegisterRequest;
import com.example.paradise.domain.user.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterRequest request) {
            userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("/login");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginRequest request) {
        try {
            String token = userService.loginUser(request.getEmail(), request.getPassword());

            return ResponseEntity.ok(JwtTokenUtil.BEARER_PREFIX + token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<User> updatePassword(@RequestBody @Valid UserPasswordUpdateRequest request) {
        User updatedUser = userService.updatePassword(request.getEmail(), request.getNewPassword(), request.getConfirmPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id, @RequestBody @Valid UserDeleteRequest request) {
        userService.deleteUser(id, request.getPassword());
        return ResponseEntity.ok("회원탈퇴 성공");
    }
}
