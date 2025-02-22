package com.example.csservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ProvisionDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
