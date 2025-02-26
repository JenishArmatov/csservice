package com.example.csservice.mappers;

import com.example.csservice.dto.BasketDto;
import com.example.csservice.dto.BasketItemDto;
import com.example.csservice.entity.Basket;
import com.example.csservice.entity.BasketItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public BasketDto toDto(Basket basket) {
        return BasketDto.builder()
                .id(basket.getId())
                .userId(basket.getUser().getId())
                .items(basket.getItems().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public BasketItemDto toDto(BasketItem basketItem) {
        return BasketItemDto.builder()
                .productId(basketItem.getProduct().getId())
                .productName(basketItem.getProduct().getName())
                .quantity(basketItem.getQuantity())
                .build();
    }
}
