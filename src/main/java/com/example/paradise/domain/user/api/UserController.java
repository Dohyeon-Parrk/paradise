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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordUpdateRequest request) {
        User updatedUser = userService.updatePassword(request);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteRequest request) {
        try {
            userService.deleteUser(request);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
