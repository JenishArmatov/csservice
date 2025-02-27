package com.example.csservice.controller;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Image;
import com.example.csservice.services.ImageService;
import com.example.csservice.services.ProductService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Товары", description = "API для управления товарами")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка товаров",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Получить товар по ID", description = "Возвращает товар по его уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар найден",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Товар не найден")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Поиск товаров по имени", description = "Возвращает список товаров по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товары найдены",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))),
            @ApiResponse(responseCode = "404", description = "Товары не найдены")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDto>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }
}

