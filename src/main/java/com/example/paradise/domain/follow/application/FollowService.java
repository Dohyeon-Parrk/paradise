package com.example.paradise.domain.follow.application;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.follow.domain.repository.FollowRepository;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long receiverId, Long requesterId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + receiverId));

        User requester = userRepository.findById(requesterId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다." + requesterId));

        Follow follow = Follow.builder()
                .receiver(receiver)
                .requester(requester)
                .build();

        followRepository.save(follow);
    }


}
