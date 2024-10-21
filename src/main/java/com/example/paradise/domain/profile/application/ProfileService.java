package com.example.paradise.domain.profile.application;

import com.example.paradise.domain.profile.dao.ProfileRepository;
import com.example.paradise.domain.profile.domain.Profile;
import com.example.paradise.domain.profile.dto.ProfileResponseDto;
import com.example.paradise.domain.profile.dto.image.ProfileImageRequestDto;
import com.example.paradise.domain.profile.dto.profile.ProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    // 프로필 조회
    public ProfileResponseDto getProfile(Long userId) {
        // 유저 찾기

        // 프로필 찾기

        return null;
    }

    // 프로필 수정
    public ProfileResponseDto updateProfile(Long userId, ProfileUpdateRequestDto profileUpdateRequestDto) {
        return null;
    }

    // 프로필 이미지 수정
    public ProfileResponseDto updateProfileImage(Long userId, ProfileImageRequestDto profileImageRequestDto) {

        return null;
    }
}
