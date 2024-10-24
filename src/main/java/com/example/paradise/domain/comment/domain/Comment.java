package com.example.paradise.domain.comment.domain;

import com.example.paradise.domain.post.domain.Post;
import com.example.paradise.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 게시글 - 유저 여러명
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    // 하나의 게시글 - 댓글 여러개
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comments;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(User author, Post post, String comments){
        this.author = author;
        this.post = post;
        this.comments = comments;
    }
}
