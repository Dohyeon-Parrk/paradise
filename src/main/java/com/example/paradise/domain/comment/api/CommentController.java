package com.example.paradise.domain.comment.api;

import com.example.paradise.domain.comment.application.CommentService;
import com.example.paradise.domain.comment.dto.CommentResponseDto;
import com.example.paradise.domain.profile.application.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 해당 프로필 주인이 작성한 댓글 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<CommentResponseDto>> getUserComments(@PathVariable Long userId){
        List<CommentResponseDto> comments = commentService.getUserComments(userId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
