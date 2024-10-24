package com.example.paradise.domain.follow.api.dto;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowingInfoResponse(
        Long userId,
        String follower,
        String profileImageUrl
) {
    public static FollowingInfoResponse from(Follow follow) {
        User follower = follow.getReceiver();
        String profileImageUrl = follower.getProfile().getProfileImage();

        return FollowingInfoResponse.builder()
                .userId(follow.getReceiver().getId())
                .follower(follow.getReceiver().getUsername())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
