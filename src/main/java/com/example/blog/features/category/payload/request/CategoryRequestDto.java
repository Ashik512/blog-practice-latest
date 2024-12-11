package com.example.blog.features.category.payload.request;

import com.example.blog.common.payloads.IDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto implements IDto {
    @NotBlank(message = "Category name must not be blank")
    private String name;
}
