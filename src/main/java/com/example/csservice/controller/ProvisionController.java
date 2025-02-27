package com.example.csservice.controller;

import com.example.csservice.dto.ProvisionDto;
import com.example.csservice.services.ProvisionService;
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
@RequestMapping("/api/provisions")
@RequiredArgsConstructor
@Tag(name = "Поставки", description = "API для управления поставками")
public class ProvisionController {
    private final ProvisionService provisionService;

    @Operation(summary = "Получить все поставки", description = "Возвращает список всех поставок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка поставок",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProvisionDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<ProvisionDto>> getAllProducts() {
        return ResponseEntity.ok(provisionService.getAllProvisions());
    }

    @Operation(summary = "Получить поставку по ID", description = "Возвращает поставку по её уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставка найдена",
                    content = @Content(schema = @Schema(implementation = ProvisionDto.class))),
            @ApiResponse(responseCode = "404", description = "Поставка не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProvisionDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(provisionService.getProvisionById(id));
    }

    @Operation(summary = "Поиск поставок по названию", description = "Возвращает список поставок по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставки найдены",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProvisionDto.class)))),
            @ApiResponse(responseCode = "404", description = "Поставки не найдены")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProvisionDto>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(provisionService.getProvisionByName(name));
    }
}

