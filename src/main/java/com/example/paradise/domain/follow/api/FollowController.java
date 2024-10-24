package com.example.paradise.domain.follow.api;

import com.example.paradise.domain.follow.api.dto.FollowListResponse;
import com.example.paradise.domain.follow.application.FollowService;
import com.example.paradise.domain.post.dto.PostResponseDto;
import com.example.paradise.domain.user.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{receiverId}")
    public ResponseEntity<String> follow(@PathVariable Long receiverId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.follow(receiverId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{receiverId}")
    public ResponseEntity<String> unFollow(@PathVariable Long receiverId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.unfollow(receiverId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{receiverId}")
    public ResponseEntity<String> acceptedFollow(@PathVariable Long receiverId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.acceptedFollow(receiverId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/check/{receiverId}")
    public ResponseEntity<Boolean> checkFollowing(@PathVariable Long receiverId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean response = followService.checkFollowing(receiverId, userDetails.getUser().getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 서로 친구인 사람만 해당 팔로우, 팔로잉을 확인할 수 있도록 해도 ㄱㅊ(비공개 계정같은)
    @GetMapping("/followers/{userId}")
    public ResponseEntity<FollowListResponse> retrieveAllFollowers(@PathVariable Long userId) {
        FollowListResponse responses = followService.retrieveAllFollowers(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> retrieveAllFollowingPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> followingPosts = followService.retrieveAllFollowingPosts(userDetails.getUser().getId());
        return new ResponseEntity<>(followingPosts, HttpStatus.OK);
    }
}
