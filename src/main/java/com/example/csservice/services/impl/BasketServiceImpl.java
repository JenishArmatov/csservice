package com.example.csservice.services.impl;

import com.example.csservice.dto.BasketDto;
import com.example.csservice.entity.Basket;
import com.example.csservice.entity.BasketItem;
import com.example.csservice.entity.Product;
import com.example.csservice.entity.User;
import com.example.csservice.mappers.BasketMapper;
import com.example.csservice.repository.BasketRepository;
import com.example.csservice.repository.ProductRepository;
import com.example.csservice.repository.UserRepository;
import com.example.csservice.services.BasketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    @Override
    public BasketDto getBasketByUserId(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Корзина пользователя не найдена"));
        return basketMapper.toDto(basket);
    }

    @Override
    public BasketDto addItemToBasket(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));

        Basket basket = basketRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Basket newBasket = new Basket();
                    newBasket.setUser(user);
                    return basketRepository.save(newBasket);
                });

        BasketItem item = new BasketItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        basket.getItems().add(item);

        basketRepository.save(basket);
        return basketMapper.toDto(basket);
    }



    @Override
    public BasketDto removeItemFromBasket(Long userId, Long productId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Корзина пользователя не найдена"));

        basket.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        basketRepository.save(basket);
        return basketMapper.toDto(basket);
    }

    @Override
    public void clearBasket(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Корзина пользователя не найдена"));

        basket.getItems().clear();
        basketRepository.save(basket);
    }
}

