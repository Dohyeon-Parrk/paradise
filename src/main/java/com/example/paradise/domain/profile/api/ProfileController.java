package com.example.paradise.domain.profile.api;

import com.example.paradise.domain.comment.dto.CommentResponseDto;
import com.example.paradise.domain.profile.application.ProfileService;
import com.example.paradise.domain.profile.dto.ProfileResponseDto;
import com.example.paradise.domain.profile.dto.image.ProfileImageRequestDto;
import com.example.paradise.domain.profile.dto.profile.ProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;


    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId){
        ProfileResponseDto profileResponseDto = profileService.getProfile(userId);

        return ResponseEntity.ok(profileResponseDto);
    }

    // 프로필 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable Long userId,
                                                            @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto){
        ProfileResponseDto updatedProfile = profileService.updateProfile(userId, profileUpdateRequestDto);

        return ResponseEntity.ok(updatedProfile);
    }

    // 프로필 이미지 수정
    @PatchMapping("/{userId}/image")
    public ResponseEntity<ProfileResponseDto> updateProfileImage(@PathVariable Long userId,
                                                                 @RequestBody ProfileImageRequestDto profileImageRequestDto){
        ProfileResponseDto updatedProfile = profileService.updateProfileImage(userId, profileImageRequestDto);

        return ResponseEntity.ok(updatedProfile);
    }
}
