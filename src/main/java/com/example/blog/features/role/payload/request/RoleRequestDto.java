package com.example.blog.features.role.payload.request;

import com.example.blog.common.payloads.IDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto implements IDto {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters")
    private String name;

}
