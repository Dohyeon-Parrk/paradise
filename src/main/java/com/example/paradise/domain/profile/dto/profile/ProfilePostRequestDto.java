package com.example.paradise.domain.profile.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePostRequestDto {

    private String username;
    private String email;
    private String bio;

}
