package com.example.blog.features.comment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWithCommentsResponseDto {
    private Long postId;
    private String title;
    private List<CommentResponseDto> comments;
}
