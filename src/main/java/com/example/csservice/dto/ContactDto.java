package com.example.csservice.dto;

import com.example.csservice.entity.Image;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ContactDto {
    private Long id;
    private String name;
    private String link;
    private Long imageId;
}
