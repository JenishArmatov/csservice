package com.example.csservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasketDto {
    private Long id; // ID корзины (может быть полезен для админки)
    private Long userId; // ID пользователя
    private List<BasketItemDto> items; // Список товаров в корзине
}