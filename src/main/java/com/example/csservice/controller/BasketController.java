package com.example.csservice.controller;

import com.example.csservice.dto.BasketDto;
import com.example.csservice.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/{userId}")
    public ResponseEntity<BasketDto> getUserBasket(@PathVariable Long userId) {
        return ResponseEntity.ok(basketService.getBasketByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<BasketDto> addItemToBasket(@RequestParam Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        return ResponseEntity.ok(basketService.addItemToBasket(userId, productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<BasketDto> removeItemFromBasket(@RequestParam Long userId,
                                                          @RequestParam Long productId) {
        return ResponseEntity.ok(basketService.removeItemFromBasket(userId, productId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long userId) {
        basketService.clearBasket(userId);
        return ResponseEntity.noContent().build();
    }
}

