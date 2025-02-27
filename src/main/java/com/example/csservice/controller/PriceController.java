package com.example.csservice.controller;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.services.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@Tag(name = "Цены", description = "API для управления ценами товаров")
public class PriceController {
    private final PriceService priceService;

    @Operation(summary = "Получить все цены", description = "Возвращает список всех цен")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка цен",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PriceDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<PriceDto>> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @Operation(summary = "Получить цену по ID", description = "Возвращает цену по её уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Цена найдена",
                    content = @Content(schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "404", description = "Цена не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PriceDto> getPriceById(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.getPriceById(id));
    }
}
