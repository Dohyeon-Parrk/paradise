package com.example.paradise.domain.follow.domain.repository;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.follow.domain.FollowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByRequesterIdAndReceiverId(Long receiverId, Long requesterId);

    List<Follow> findAllByRequesterIdAndStatus(Long userId, FollowStatus status);
}
