package com.example.paradise.domain.user.application;

import com.example.paradise.domain.profile.application.ProfileService;
import com.example.paradise.domain.user.config.PasswordEncoder;
import com.example.paradise.domain.user.domain.UserRoleEnum;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import com.example.paradise.domain.user.dto.UserRegisterRequest;
import com.example.paradise.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    // 회원 가입
    public User registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmailAndStatus(request.getEmail(), "ACTIVE")) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getUsername(), UserRoleEnum.USER);
        userRepository.save(user);

        // 회원가입 후 자동으로 기본 프로필 생성
        profileService.createProfile(userRepository.save(user));
        return user;
    }
    // 로그인
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
    // 모든 회원 조회(ADMIN 권한 필요)
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // 특정 회원 조회(ADMIN 권한 필요)
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    // 비밀번호 변경
    public User updatePassword(String email, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.changePassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
    // 회원 탈퇴
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
