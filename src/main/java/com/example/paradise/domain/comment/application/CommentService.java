package com.example.paradise.domain.comment.application;

import com.example.paradise.domain.comment.dao.CommentRepository;
import com.example.paradise.domain.comment.domain.Comment;
import com.example.paradise.domain.comment.dto.CommentRequestDto;
import com.example.paradise.domain.comment.dto.CommentResponseDto;
import com.example.paradise.domain.post.domain.Post;
import com.example.paradise.domain.post.domain.PostRepository;
import com.example.paradise.domain.profile.dao.ProfileRepository;
import com.example.paradise.domain.profile.domain.Profile;
import com.example.paradise.domain.user.domain.User;
import com.example.paradise.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 해당 프로필 주인이 작성한 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getUserComments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다."));

        return commentRepository.findByUserId(userId).stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("포스팅된 글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(), user.getId(), user.getUsername(), savedComment.getContent(), savedComment.getCreatedAt());
    }
}
