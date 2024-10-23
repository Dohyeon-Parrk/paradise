package com.example.paradise.domain.comment.api;

import com.example.paradise.domain.comment.application.CommentService;
import com.example.paradise.domain.comment.dto.CommentRequestDto;
import com.example.paradise.domain.comment.dto.CommentResponseDto;
import com.example.paradise.domain.profile.application.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 댓글 생성
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
