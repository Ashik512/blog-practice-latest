package com.example.blog.common.payloads.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    private String password;
}
