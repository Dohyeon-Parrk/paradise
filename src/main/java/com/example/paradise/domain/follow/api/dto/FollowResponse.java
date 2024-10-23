package com.example.paradise.domain.follow.api.dto;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowResponse(
        Long id,
        String follower,
        String profileImageUrl
) {
    public static FollowResponse from(Follow follow) {
        User follower = follow.getReceiver();
        String profileImageUrl = follower.getProfile().getProfileImage();
        return FollowResponse.builder()
                .id(follow.getId())
                .follower(follow.getReceiver().getUsername())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
