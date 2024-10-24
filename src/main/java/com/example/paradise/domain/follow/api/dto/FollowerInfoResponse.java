package com.example.paradise.domain.follow.api.dto;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowerInfoResponse(
        Long userId,
        String follower,
        String profileImageUrl
) {
    public static FollowerInfoResponse from(Follow follow) {
        User follower = follow.getRequester();
        String profileImageUrl = follower.getProfile().getProfileImage();

        return FollowerInfoResponse.builder()
                .userId(follow.getRequester().getId())
                .follower(follow.getRequester().getUsername())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
