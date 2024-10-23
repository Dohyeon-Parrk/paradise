package com.example.paradise.domain.follow.application;

import com.example.paradise.domain.follow.api.dto.FollowInfoResponse;
import com.example.paradise.domain.follow.api.dto.FollowListResponse;
import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.follow.domain.FollowStatus;
import com.example.paradise.domain.follow.domain.repository.FollowRepository;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long receiverId, Long userId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + receiverId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + userId));

        Follow follow = Follow.builder()
                .receiver(receiver)
                .requester(user)
                .build();

        followRepository.save(follow);
    }

    @Transactional  // 팔로우 요청 거절 또는 언팔로우
    public void unfollow(Long requesterId, Long userId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + requesterId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + userId));

        Follow follow = followRepository.findByRequesterIdAndReceiverId(requesterId, userId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));

        followRepository.delete(follow);
    }

    @Transactional
    public void acceptedFollow(Long requesterId, Long userId) {
        Follow follow = followRepository.findByRequesterIdAndReceiverId(requesterId, userId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));
        follow.updateFollowStatus(FollowStatus.ACCEPTED);
        followRepository.save(follow);
    }

    public boolean checkFollowing(Long receiverId, Long userId) {
        Follow follow = followRepository.findByRequesterIdAndReceiverId(receiverId, userId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));
        return follow.isFollowing();
    }

    public FollowListResponse retrieveAllFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + userId));
        List<Follow> followers = followRepository.findAllByRequesterIdAndStatus(userId, FollowStatus.ACCEPTED);
        List<FollowInfoResponse> followInfoResponses = followers.stream()
                .map(FollowInfoResponse::from)
                .toList();
        return FollowListResponse.from(followInfoResponses);
    }
}
