package com.example.csservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItemDto {
    private Long productId;
    private String productName;
    private Integer quantity;
}