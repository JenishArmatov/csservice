package com.example.csservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Long id;
    private String tagName;
}

