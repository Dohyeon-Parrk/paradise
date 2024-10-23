package com.example.paradise.domain.follow.api.dto;

import com.example.paradise.domain.follow.domain.Follow;
import com.example.paradise.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowInfoResponse(
        Long id,
        String follower,
        String profileImageUrl
) {
    public static FollowInfoResponse from(Follow follow) {
        User follower = follow.getReceiver();
        String profileImageUrl = follower.getProfile().getProfileImage();

        return FollowInfoResponse.builder()
                .id(follow.getId())
                .follower(follow.getReceiver().getUsername())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
