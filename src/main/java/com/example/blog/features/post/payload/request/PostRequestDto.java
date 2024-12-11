package com.example.blog.features.post.payload.request;

import com.example.blog.common.payloads.IDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto implements IDto {
    @NotBlank(message = "Title must not be blank")
    private String title;
    private String content;
    private String image;
    @NotNull(message = "User-Id must not be null")
    private Long userId;
    @NotNull(message = "Category-Id must not be null")
    private Long categoryId;
}
