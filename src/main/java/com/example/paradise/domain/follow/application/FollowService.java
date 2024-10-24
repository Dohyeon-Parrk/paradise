package com.example.paradise.domain.follow.application;

import com.example.paradise.domain.follow.api.dto.FollowInfoResponse;
import com.example.paradise.domain.follow.api.dto.FollowListResponse;
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

import java.util.Comparator;
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
        User receiver = findUserById(receiverId);

        User user = findUserById(userId);

        Follow follow = Follow.builder()
                .receiver(receiver)
                .requester(user)
                .build();

        followRepository.save(follow);
    }

    @Transactional  // 팔로우 요청 거절 또는 언팔로우
    public void unfollow(Long followerId, Long userId) {
        findUserById(followerId);
        findUserById(userId);

        Follow follow = followRepository.findByRequesterIdAndReceiverId(userId, followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));

        followRepository.delete(follow);
    }

    @Transactional
    public void acceptedFollow(Long otherId, Long userId) {
        findUserById(otherId);
        findUserById(userId);

        Follow follow = followRepository.findByRequesterIdAndReceiverId(userId, otherId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 서로 요청하지 않은 상태입니다."));
        follow.updateFollowStatus(FollowStatus.ACCEPTED);
        followRepository.save(follow);
    }

    public boolean checkFollowing(Long receiverId, Long userId) {
        Optional<Follow> follow = followRepository.findByRequesterIdAndReceiverId(userId, receiverId);

        if (follow.isEmpty() || follow.get().getStatus().equals(FollowStatus.PENDING)) {
            return false;
        }

        return follow.get().isFollowing();
    }


    public FollowListResponse retrieveAllFollowers(Long userId) {
        findUserById(userId);
        List<Follow> follows = followRepository.findAllByRequesterIdAndStatus(userId, FollowStatus.ACCEPTED);
        List<FollowInfoResponse> followInfoResponses = follows.stream()
                .map(FollowInfoResponse::from)
                .toList();
        return FollowListResponse.from(followInfoResponses);
    }

    public List<PostResponseDto> retrieveAllFollowingPosts(Long userId) {
        findUserById(userId);
        List<Follow> follows = followRepository.findAllByRequesterIdAndStatus(userId, FollowStatus.ACCEPTED);
        // follow -> user가 팔로우하고 있는 사람들(receiver) -> posts들 가져오기 -> 나열은 생성일 순.
        // 그리고 페이지네이션은 고민.

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
