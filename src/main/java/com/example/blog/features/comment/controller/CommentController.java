package com.example.blog.features.comment.controller;

import com.example.blog.common.payloads.response.MessageResponse;
import com.example.blog.features.comment.payload.request.CommentRequestDto;
import com.example.blog.features.comment.payload.response.CommentWithPostResponseDto;
import com.example.blog.features.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blog.common.constants.ApplicationConstant.CREATED_SUCCESSFULLY_MESSAGE;

@RestController
@RequestMapping("/api/post/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(commentRequestDto);

        return ResponseEntity.ok(new MessageResponse(CREATED_SUCCESSFULLY_MESSAGE));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommentWithPostResponseDto> getCommentsWithPostId(@PathVariable Long postId) {
        CommentWithPostResponseDto response = commentService.getAllCommentWithPostByPostID(postId);

        return ResponseEntity.ok(response);
    }
}
