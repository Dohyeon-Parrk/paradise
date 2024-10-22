package com.example.paradise.domain.follow.api;

import com.example.paradise.domain.follow.application.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{receiverId}")
    public ResponseEntity<String> unFollow(@PathVariable Long receiverId) {
        followService.unfollow(receiverId, 1L);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
