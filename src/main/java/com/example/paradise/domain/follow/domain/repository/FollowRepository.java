package com.example.paradise.domain.follow.domain.repository;

import com.example.paradise.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByReceiverIdAndRequesterId(Long receiverId, Long requesterId);
}
