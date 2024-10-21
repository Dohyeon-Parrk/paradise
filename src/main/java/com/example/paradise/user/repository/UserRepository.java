package com.example.paradise.user.repository;

import com.example.paradise.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmailAndEmailNotContaining(String email, String deletedSuffix); // 특정 이메일 주소가 특정 문자열을 포함하지 않는 지 확인 및 새로운 사용자 등록 방지
}
