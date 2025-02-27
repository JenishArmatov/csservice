package com.example.csservice.controller;

import com.example.csservice.dto.CategoryDto;
import com.example.csservice.dto.ClientDto;
import com.example.csservice.services.CategoryService;
import com.example.csservice.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Категории", description = "API для управления категориями товаров")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Получить все категории", description = "Возвращает список всех категорий товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка категорий",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Получить категорию по ID", description = "Возвращает категорию по её уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория найдена",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Получить категорию по имени", description = "Возвращает список категорий по их названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категории найдены",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "404", description = "Категории не найдены")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<CategoryDto>> getCategoryByName(
            @Parameter(description = "Название категории", example = "Электроника") @PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }
}
