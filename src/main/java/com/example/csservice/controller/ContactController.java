package com.example.csservice.controller;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ContactDto;
import com.example.csservice.entity.Image;
import com.example.csservice.services.ClientService;
import com.example.csservice.services.ContactService;
import com.example.csservice.services.ImageService;
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
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Контакты", description = "API для управления контактами")
public class ContactController {
    private final ContactService contactService;

    @Operation(summary = "Получить все контакты", description = "Возвращает список всех контактов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен список контактов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @Operation(summary = "Получить контакт по ID", description = "Возвращает контакт по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакт найден",
                    content = @Content(schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "404", description = "Контакт не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(
            @Parameter(description = "ID контакта", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @Operation(summary = "Поиск контактов по имени", description = "Возвращает список контактов по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакты найдены",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(responseCode = "404", description = "Контакты не найдены")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ContactDto>> getContactByName(
            @Parameter(description = "Имя контакта", example = "Иван") @RequestParam String name) {
        return ResponseEntity.ok(contactService.getContactByName(name));
    }
}

