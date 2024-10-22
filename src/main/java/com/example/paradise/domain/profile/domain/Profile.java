package com.example.paradise.domain.profile.domain;

import com.example.paradise.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User 테이블과 1:1
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "profile_image")
    private String profileImage;    // 프로필 이미지 URL

    @Column(name = "bio")
    private String bio;     // 자기소개

    @Column(name = "follower")
    private Long follower = 0L;     // 팔로워 수

    @Column(name = "following")
    private Long following = 0L;    // 팔로잉 수

}
