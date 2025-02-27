package com.example.csservice.controller;

import com.example.csservice.dto.ManufacturerDto;
import com.example.csservice.services.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@RequiredArgsConstructor
@Tag(name = "Производители", description = "API для управления производителями товаров")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @Operation(summary = "Получить производителя по ID", description = "Возвращает производителя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Производитель найден",
                    content = @Content(schema = @Schema(implementation = ManufacturerDto.class))),
            @ApiResponse(responseCode = "404", description = "Производитель не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDto> getManufacturerById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @Operation(summary = "Получить производителя по имени", description = "Возвращает производителя по его названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Производитель найден",
                    content = @Content(schema = @Schema(implementation = ManufacturerDto.class))),
            @ApiResponse(responseCode = "404", description = "Производитель не найден")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ManufacturerDto> getManufacturerByName(@PathVariable String name) {
        return ResponseEntity.ok(manufacturerService.getManufacturerByName(name));
    }

    @Operation(summary = "Получить всех производителей", description = "Возвращает список всех производителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка производителей",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ManufacturerDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }
}

