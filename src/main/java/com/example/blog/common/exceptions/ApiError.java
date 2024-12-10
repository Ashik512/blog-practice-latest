package com.example.blog.common.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {
    List<BlogError> apiErrors;
    public void addError(BlogError error) {
        if(apiErrors == null) {
            apiErrors = new ArrayList<>();
        }
        apiErrors.add(error);
    }
}
