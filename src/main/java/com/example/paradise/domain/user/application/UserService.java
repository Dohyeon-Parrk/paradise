package com.example.paradise.domain.user.application;

import com.example.paradise.domain.profile.application.ProfileService;

import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.UserRoleEnum;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import com.example.paradise.domain.user.dto.UserRegisterRequest;

import com.example.paradise.domain.user.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final JwtTokenUtil jwtTokenUtil;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void registerUser(UserRegisterRequest request) {
        String email = request.getEmail();
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        Optional<User> checkUsername = userRepository.findByEmail(email);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        if (request.isAdmin()) {
            if (!ADMIN_TOKEN.equals(request.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, email, password, role);
        // 회원가입 후 자동으로 기본 프로필 생성
        userRepository.save(user);
        profileService.createProfile(user);
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String role = user.getRoleAsString();

        return jwtTokenUtil.createToken(user.getEmail(), role);
    }
    // ADMIN 권한 필요
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // ADMIN 권한 필요
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updatePassword(String email, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.changePassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public void deleteUser(Long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.deactiveAccount(); // 계정을 비활성화 상태로 변경
        userRepository.save(user);
    }
}
