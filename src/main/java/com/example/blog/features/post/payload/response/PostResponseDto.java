package com.example.blog.features.post.payload.response;

import com.example.blog.features.category.payload.response.CategoryResponseDto;
import com.example.blog.features.user.payload.response.UserResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private UserResponseDto userResponseDto;
    private CategoryResponseDto categoryResponseDto;
}
