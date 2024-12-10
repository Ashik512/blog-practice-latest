package com.example.blog.common.exceptions;

public class BlogError {
    private String code;
    private String message;

    public BlogError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
