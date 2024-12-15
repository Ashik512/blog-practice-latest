package com.example.blog.features.comment.controller;

import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.response.MessageResponse;
import com.example.blog.features.comment.payload.request.CommentRequestDto;
import com.example.blog.features.comment.payload.response.CommentWithPostResponseDto;
import com.example.blog.features.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.example.blog.common.constants.ApplicationConstant.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createComment(@RequestParam Long postId,
                                                         @RequestParam Long userId,
                                                         @RequestBody CommentRequestDto commentRequestDto) {

        commentService.createComment(postId, userId, commentRequestDto);

        return ResponseEntity.ok(new MessageResponse(CREATED_SUCCESSFULLY_MESSAGE));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<MessageResponse> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId, userId, commentRequestDto);

        return ResponseEntity.ok(new MessageResponse(UPDATED_SUCCESSFULLY_MESSAGE));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(new MessageResponse(DELETED_SUCCESSFULLY));
    }


    @GetMapping
    public ResponseEntity<CommentWithPostResponseDto> getCommentsWithPostId(@RequestParam Long postId) {
        CommentWithPostResponseDto response = commentService.getAllCommentWithPostByPostID(postId);

        return ResponseEntity.ok(response);
    }

}
