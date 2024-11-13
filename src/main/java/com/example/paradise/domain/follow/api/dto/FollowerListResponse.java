package com.example.paradise.domain.follow.api.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record FollowerListResponse(
        List<FollowerInfoResponse> followers
) {
    public static FollowerListResponse from(List<FollowerInfoResponse> followers) {
        return FollowerListResponse.builder()
                .followers(followers)
                .build();
    }
}
