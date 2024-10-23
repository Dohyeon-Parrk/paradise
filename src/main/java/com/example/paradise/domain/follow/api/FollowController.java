package com.example.paradise.domain.follow.api;

import com.example.paradise.domain.follow.api.dto.FollowResponse;
import com.example.paradise.domain.follow.application.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;
// 추후 user쪽 완성되면 쿠키에서 토큰 가져와서 사용자 인증!!
    @PostMapping("/{receiverId}")
    public ResponseEntity<String> follow(@PathVariable Long receiverId){
        followService.follow(receiverId, 1L);
        return new ResponseEntity<>(HttpStatus.OK);
    }
// 추후 user쪽 완성되면 쿠키에서 토큰 가져와서 사용자 인증!!
    @DeleteMapping("/{requesterId}")
    public ResponseEntity<String> unFollow(@PathVariable Long requesterId) {
        followService.unfollow(requesterId, 1L);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 서로 친구인 사람만 해당 팔로우, 팔로잉을 확인할 수 있도록 해도 ㄱㅊ(비공개 계정같은)
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowResponse>> retrieveAllFollowers(@PathVariable Long userId) {
        List<FollowResponse> responses = followService.retrieveAllFollowers(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
