package com.example.csservice.services;

import com.example.csservice.dto.BasketDto;

import java.util.List;

public interface BasketService {
    BasketDto getBasketByUserId(Long userId);
    BasketDto addItemToBasket(Long userId, Long productId, Integer quantity);
    BasketDto removeItemFromBasket(Long userId, Long productId);
    void clearBasket(Long userId);
}