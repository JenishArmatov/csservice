package com.example.csservice.controller;

import com.example.csservice.dto.CategoryDto;
import com.example.csservice.dto.ClientDto;
import com.example.csservice.services.CategoryService;
import com.example.csservice.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Получить категорию по ID", description = "Возвращает категорию по её уникальному идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Получить категорию по имени", description = "Возвращает список категорий по их названию")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<CategoryDto>> getCategoryByName(
            @Parameter(description = "Название категории", example = "Электроника") @PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

}