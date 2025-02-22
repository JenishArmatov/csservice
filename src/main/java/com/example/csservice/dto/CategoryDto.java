package com.example.csservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;

    private String url;

    private String contentType;
}
