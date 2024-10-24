package com.example.paradise.domain.follow.api.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record FollowingListResponse(
        List<FollowingInfoResponse> followings
) {
    public static FollowingListResponse from(List<FollowingInfoResponse> followings) {
        return FollowingListResponse.builder()
                .followings(followings)
                .build();
    }
}
