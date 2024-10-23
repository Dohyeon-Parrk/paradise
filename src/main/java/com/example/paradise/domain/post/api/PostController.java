package com.example.paradise.domain.post.api;

import com.example.paradise.domain.post.application.PostService;
import com.example.paradise.domain.post.dto.CreatePostRequestDto;
import com.example.paradise.domain.post.dto.PostResponseDto;
import com.example.paradise.domain.post.dto.UpdatePostRequestDto;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;


    // 1. 게시글 작성
    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody CreatePostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 사용자 정보 추출
        User user = userDetails.getUser();

        // 게시글 생성 서비스 호출
        PostResponseDto response = postService.createPost(requestDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // 2. 게시글 조회 - 전체
    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto> > getAllPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("updatedAt").descending());
        Page<PostResponseDto> response = postService.getAllPosts(pageable,userDetails.getUser());
        return  ResponseEntity.ok(response);
    }

    // 3. 게시글 조회 - 단건
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.getPostById(postId,userDetails.getUser()));
    }


    // 3. 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Void>updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(postId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    // 4. 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
