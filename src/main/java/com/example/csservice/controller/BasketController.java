package com.example.csservice.controller;

import com.example.csservice.dto.BasketDto;
import com.example.csservice.services.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
@Tag(name = "Basket Controller", description = "Управление корзиной пользователей")
public class BasketController {
    private final BasketService basketService;

    @Operation(summary = "Получение корзины пользователя", description = "Возвращает содержимое корзины по ID пользователя.")
    @ApiResponse(responseCode = "200", description = "Корзина успешно получена",
            content = @Content(schema = @Schema(implementation = BasketDto.class)))
    @GetMapping("/{userId}")
    public ResponseEntity<BasketDto> getUserBasket(@PathVariable Long userId) {
        return ResponseEntity.ok(basketService.getBasketByUserId(userId));
    }

    @Operation(summary = "Добавление товара в корзину", description = "Добавляет товар в корзину пользователя.")
    @ApiResponse(responseCode = "200", description = "Товар успешно добавлен в корзину",
            content = @Content(schema = @Schema(implementation = BasketDto.class)))
    @PostMapping("/add")
    public ResponseEntity<BasketDto> addItemToBasket(@RequestParam Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        return ResponseEntity.ok(basketService.addItemToBasket(userId, productId, quantity));
    }

    @Operation(summary = "Удаление товара из корзины", description = "Удаляет указанный товар из корзины пользователя.")
    @ApiResponse(responseCode = "200", description = "Товар успешно удален из корзины",
            content = @Content(schema = @Schema(implementation = BasketDto.class)))
    @DeleteMapping("/remove")
    public ResponseEntity<BasketDto> removeItemFromBasket(@RequestParam Long userId,
                                                          @RequestParam Long productId) {
        return ResponseEntity.ok(basketService.removeItemFromBasket(userId, productId));
    }

    @Operation(summary = "Очистка корзины пользователя", description = "Удаляет все товары из корзины пользователя.")
    @ApiResponse(responseCode = "204", description = "Корзина успешно очищена")
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long userId) {
        basketService.clearBasket(userId);
        return ResponseEntity.noContent().build();
    }
}


