package com.example.paradise.domain.comment.api;

import com.example.paradise.domain.comment.application.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


}
