package com.example.paradise.domain.profile.dto;

import com.example.paradise.domain.profile.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String profileImage;
    private String bio;
    private Long follower;
    private Long following;

    public ProfileResponseDto(Profile profile) {
        this.profileImage = profile.getProfileImage();
        this.bio = profile.getBio();
        this.follower = profile.getFollower();
        this.following = profile.getFollowing();
    }
}
