package com.example.paradise.domain.post.domain;

import com.example.paradise.common.Timestamped;

import com.example.paradise.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    public Post(User user, String content) {
        this.username = user.getUsername();
        this.content = content;
    }

    // 게시글 업데이트 메서드
    public void updateContent(String content) {
        this.content = content;

    }


}
