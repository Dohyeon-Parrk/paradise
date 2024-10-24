package com.example.paradise.domain.post.domain;

import com.example.paradise.common.Timestamped;

import com.example.paradise.domain.comment.domain.Comment;
import com.example.paradise.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, updatable = false)
    private Long postId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    //게시글 - 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    // 게시글 - 댓글
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(User user, String content) {
        this.username = user.getUsername();
        this.content = content;
        this.author = user;
    }

    // 게시글 업데이트 메서드
    public void updateContent(String content) {
        this.content = content;
    }

    // 댓글 추가 메소드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);  // 양방향 연관관계 설정
    }

    // 댓글 삭제 메소드
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }


}
