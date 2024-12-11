package com.example.blog.features.user.payload.response;

import com.example.blog.features.role.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private Role role;
}
