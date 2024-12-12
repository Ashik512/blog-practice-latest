package com.example.blog.features.comment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "comment must not be null")
    private String comment;
    @NotNull(message = "User-Id must not be null")
    private Long userId;
    @NotNull(message = "Post-Id must not be null")
    private Long postId;
    private Long parentCommentId;
}
