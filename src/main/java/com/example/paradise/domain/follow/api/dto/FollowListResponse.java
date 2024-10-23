package com.example.paradise.domain.follow.api.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record FollowListResponse(
        List<FollowInfoResponse> followers
) {
    public static FollowListResponse from(List<FollowInfoResponse> followers) {
        return FollowListResponse.builder()
                .followers(followers)
                .build();
    }
}
