package com.example.blog.common.payloads.request;

import com.example.blog.common.payloads.SDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdQuerySearchDto implements SDto {
    private Long id;
    private String query;
    private Boolean isActive = true;
}
