package com.example.blog.features.user.payload.request;

import com.example.blog.common.payloads.IDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto implements IDto {
    private String name;
    private String password;
    private Long roleId;
}
