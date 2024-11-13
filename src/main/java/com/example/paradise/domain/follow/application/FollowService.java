package com.example.paradise.domain.follow.application;

import com.example.paradise.domain.follow.api.dto.FollowerInfoResponse;
import com.example.paradise.domain.follow.api.dto.FollowerListResponse;
import com.example.paradise.domain.follow.api.dto.FollowingInfoResponse;
import com.example.paradise.domain.follow.api.dto.FollowingListResponse;
import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.follow.domain.FollowStatus;
import com.example.paradise.domain.follow.domain.repository.FollowRepository;
import com.example.paradise.domain.post.domain.Post;
import com.example.paradise.domain.post.domain.PostRepository;
import com.example.paradise.domain.post.dto.PostResponseDto;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import com.example.paradise.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void follow(Long receiverId, Long userId) {
        if (receiverId.equals(userId)) {
            throw new IllegalArgumentException("자신한테 팔로우 요청을 보낼 수 없습니다.");
        }
        User receiver = findUserById(receiverId);
        User user = findUserById(userId);
        if (followRepository.existsByReceiverIdAndRequesterId(receiverId, userId)){
            throw new IllegalArgumentException("이미 팔로우 요청이 존재합니다.");
        };

        Follow follow = Follow.builder()
                .receiver(receiver)
                .requester(user)
                .build();

        followRepository.save(follow);
    }

    @Transactional  // 팔로우 요청 거절 또는 언팔로우
    public void unfollow(Long requesterId, Long userId) {
        findUserById(requesterId);
        findUserById(userId);

        Follow follow = followRepository.findByRequesterIdAndReceiverId(requesterId, userId)
                .orElseThrow(() -> new IllegalArgumentException("상대가 팔로우를 요청하지 않은 상태입니다."));

        followRepository.delete(follow);
    }

    @Transactional
    public void acceptedFollow(Long requesterId, Long userId) {
        findUserById(requesterId);
        findUserById(userId);

        Follow follow = followRepository.findByRequesterIdAndReceiverId(requesterId, userId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));
        follow.updateFollowStatus(FollowStatus.ACCEPTED);
        followRepository.save(follow);
    }

    public boolean checkFollowing(Long requesterId, Long receiverId) {
        Optional<Follow> follow = followRepository.findByRequesterIdAndReceiverId(requesterId, receiverId);
        if (follow.isEmpty() || follow.get().getStatus().equals(FollowStatus.PENDING)) {
            return false;
        }
        return follow.get().isFollowing();
    }

    public FollowerListResponse retrieveAllFollowers(Long userId) {
        findUserById(userId);
        List<Follow> follows = followRepository.findAllByReceiverIdAndStatus(userId, FollowStatus.ACCEPTED);
        List<FollowerInfoResponse> followers = follows.stream()
                .map(FollowerInfoResponse::from)
                .toList();
        return FollowerListResponse.from(followers);
    }

    public FollowingListResponse retrieveAllFollowings(Long userId) {
        findUserById(userId);
        List<Follow> follows = followRepository.findAllByRequesterIdAndStatus(userId, FollowStatus.ACCEPTED);
        List<FollowingInfoResponse> followings = follows.stream()
                .map(FollowingInfoResponse::from)
                .toList();
        return FollowingListResponse.from(followings);
    }

    public List<PostResponseDto> retrieveAllFollowingPosts(Long userId) {
        findUserById(userId);
        List<Follow> follows = followRepository.findAllByRequesterIdAndStatus(userId, FollowStatus.ACCEPTED);

        List<User> followedUsers = follows.stream()
                .map(Follow::getReceiver)
                .toList();

        List<Long> followedUserIds = followedUsers.stream()
                .map(User::getId)
                .toList();

        List<Post> followedPosts = postRepository.findPostsByUsers(followedUserIds);

        return followedPosts.stream()
                .map(PostResponseDto::new)
                .toList();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
