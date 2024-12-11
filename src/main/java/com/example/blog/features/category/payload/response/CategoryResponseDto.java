package com.example.blog.features.category.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
}
