package com.example.paradise.domain.post.application;

import com.example.paradise.domain.post.domain.Post;
import com.example.paradise.domain.post.domain.PostRepository;
import com.example.paradise.domain.post.dto.CreatePostRequestDto;
import com.example.paradise.domain.post.dto.PostResponseDto;
import com.example.paradise.domain.post.dto.UpdatePostRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    static private Long userId = 1L;
    static private String username = "홍길동";

    // 1. 게시글 등록
    public PostResponseDto createPost(CreatePostRequestDto requestDto) {
        // dto -> entity
        Post post = new Post(userId,username, requestDto.getContent());
        
        // 저장
        Post savedpost = postRepository.save(post);
        
        // dto 변환
        PostResponseDto postResponseDto = new PostResponseDto(savedpost);
        return postResponseDto;
    }


    // 2. 게시글 조회 - 전체
    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        // 게시글 페이징 처리
        Page<Post> postPage = postRepository.findAll(pageable);
        // entity -> responsedto
        return postPage.map(PostResponseDto::new);
    }

    // 3. 게시글 조회 - 단건
    public PostResponseDto getPostById(Long postId) {
        // 유효한 게시글인지  확인
        Post post = checkById(postId);

        return new PostResponseDto(
                post.getPostId(),
                post.getContent(),
                post.getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    //4. 게시글 수정
    public void updatePost(Long postId, UpdatePostRequestDto requestDto) {
        // 유효한 게시글인지  확인
        Post post = checkById(postId);

        // 게시글 내용 업데이트
        post.updateContent(requestDto.getContent());

        // 저장
        Post updatedPost = postRepository.save(post);

    }

    //5. 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        // 유효한 게시글인지  확인
        Post post = checkById(postId);

        // 게시글 삭제
        postRepository.deleteById(postId);
    }
    
    // postId 게시글 확인 메소드
    public Post checkById(Long postId){
        return postRepository.findById(postId).orElseThrow(()-> new EntityNotFoundException("게시글이 존재하지 않습니다."));
    }

}




