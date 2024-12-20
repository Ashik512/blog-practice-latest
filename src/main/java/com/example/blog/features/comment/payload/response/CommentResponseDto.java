package com.example.blog.features.comment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private String comment;
    private List<CommentResponseDto> replies = new ArrayList<>();
}
