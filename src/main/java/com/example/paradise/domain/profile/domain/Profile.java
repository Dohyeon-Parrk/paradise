package com.example.paradise.domain.profile.domain;

import com.example.paradise.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User 테이블과 1:1
    @OneToOne(fetch = FetchType.LAZY)
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

    public void updateBio(String bio){
        this.bio = bio;
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}
