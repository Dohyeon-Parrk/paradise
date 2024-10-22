package com.example.paradise.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false,updatable = false)
    private Long postId;


    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "username", nullable = false)
    private String username;


    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    // User 테이블이 있을 떄 사용
    //게시글 - 유저
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name="user_id")
    //    private User user;
    //
    //    public Post(String content, User user) {
    //        this.content = content;
    //        this.user = user;
    //    }

    // user 테이블이 없다고 할 때
    private Long userId;
    public Post(Long userId,String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }


    // 게시글 업데이트 메서드
    public void updateContent(String content) {
        this.content = content;

    }



}
