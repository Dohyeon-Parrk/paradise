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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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

        return commentRepository.findByAuthor_Id(userId).stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스팅된 글을 찾을 수 없습니다."));

        Comment comment = new Comment(user, post, commentRequestDto.getComments());
        post.addComment(comment);

        return new CommentResponseDto(comment.getId(), post.getContent(), comment.getComments(), comment.getCreatedAt());
    }

    // 댓글 삭제
    public ResponseEntity<String> deleteComment(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 댓글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        if(!comment.getAuthor().getId().equals(userId)){
            throw new IllegalArgumentException("해당 댓글은 작성자만 삭제 할 수 있습니다. 삭제 권한이 업습니다.");
        }

        commentRepository.delete(comment);

        return new ResponseEntity<>("삭제성공", HttpStatus.OK);
    }
}
