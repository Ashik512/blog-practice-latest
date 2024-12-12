package com.example.blog.features.comment.service;

import com.example.blog.common.payloads.response.MessageResponse;
import com.example.blog.features.comment.model.Comment;
import com.example.blog.features.comment.payload.request.CommentRequestDto;
import com.example.blog.features.comment.payload.response.CommentResponseDto;
import com.example.blog.features.comment.payload.response.CommentWithPostResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    void createComment(CommentRequestDto commentRequestDto);
    CommentWithPostResponseDto getAllCommentWithPostByPostID(Long postId);

}
