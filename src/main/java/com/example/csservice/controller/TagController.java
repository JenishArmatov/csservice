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

    @Operation(summary = "Получить тег по ID", description = "Возвращает тег по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег найден",
                    content = @Content(schema = @Schema(implementation = TagDto.class))),
            @ApiResponse(responseCode = "404", description = "Тег не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }



    @Operation(summary = "Поиск тега по имени", description = "Возвращает список тегов, содержащих указанное имя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список найденных тегов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagDto.class)))),
            @ApiResponse(responseCode = "404", description = "Теги не найдены")
    })
    @GetMapping("/search")
    public ResponseEntity<List<TagDto>> searchTagsByName(@RequestParam String name) {
        return ResponseEntity.ok(tagService.getTagsByTagName(name));
    }



}


