package com.example.paradise.domain.follow.api;

import com.example.paradise.domain.follow.api.dto.FollowerListResponse;
import com.example.paradise.domain.follow.api.dto.FollowingListResponse;
import com.example.paradise.domain.follow.application.FollowService;
import com.example.paradise.domain.post.dto.PostResponseDto;
import com.example.paradise.domain.user.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{requesterId}")
    public ResponseEntity<String> acceptedFollow(@PathVariable Long requesterId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.acceptedFollow(requesterId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("{requesterId}/check/{receiverId}")
    public ResponseEntity<Boolean> checkFollowing(@PathVariable Long requesterId,
                                                  @PathVariable Long receiverId) {
        boolean response = followService.checkFollowing(requesterId, receiverId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<FollowerListResponse> retrieveAllFollowers(@PathVariable Long userId) {
        FollowerListResponse responses = followService.retrieveAllFollowers(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/followings/{userId}")
    public ResponseEntity<FollowingListResponse> retrieveAllFollowings(@PathVariable Long userId) {
        FollowingListResponse responses = followService.retrieveAllFollowings(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> retrieveAllFollowingPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> followingPosts = followService.retrieveAllFollowingPosts(userDetails.getUser().getId());
        return new ResponseEntity<>(followingPosts, HttpStatus.OK);
    }
}
