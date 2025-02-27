package com.example.csservice.controller;

import com.example.csservice.dto.TagDto;
import com.example.csservice.services.TagService;
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
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Теги", description = "API для управления тегами")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Создать тег", description = "Создаёт новый тег")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег успешно создан",
                    content = @Content(schema = @Schema(implementation = TagDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @Operation(summary = "Получить все теги", description = "Возвращает список всех тегов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка тегов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}

