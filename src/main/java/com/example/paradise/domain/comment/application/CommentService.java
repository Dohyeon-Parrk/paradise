package com.example.paradise.domain.comment.application;

import com.example.paradise.domain.comment.dao.CommentRepository;
import com.example.paradise.domain.profile.dao.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;

}
