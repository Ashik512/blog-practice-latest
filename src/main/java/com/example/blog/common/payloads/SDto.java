package com.example.blog.common.payloads;

public interface SDto {
    default Boolean getIsActive() {
        return true;
    }
    default Boolean getIsDesc(){
        return false;
    }
}
