package com.example.blog.common.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlogError {
    private String code;
    private String message;

    public BlogError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
