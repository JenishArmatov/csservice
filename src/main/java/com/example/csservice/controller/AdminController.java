package com.example.csservice.controller;

import com.example.csservice.dto.*;
import com.example.csservice.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin Controller", description = "Управление администрацией и ресурсами")
public class AdminController {

    private final ClientService clientService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final ContactService contactService;

    @Operation(summary = "Получение информации о панели администратора")
    @GetMapping
    public ResponseEntity<String> getAdminInfo() {
        return ResponseEntity.ok("Добро пожаловать в панель администратора!");
    }

    @Operation(summary = "Создание клиента")
    @ApiResponse(responseCode = "200", description = "Клиент создан",
            content = @Content(schema = @Schema(implementation = ClientDto.class)))
    @PostMapping("/clients")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto,
                                                  @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(clientService.createClient(clientDto, file));
    }

    @Operation(summary = "Получение всех клиентов")
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Operation(summary = "Получение клиента по ID")
    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @Operation(summary = "Поиск клиента по имени")
    @GetMapping("/clients/search")
    public ResponseEntity<List<ClientDto>> getClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }

    @Operation(summary = "Обновление клиента")
    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDto));
    }

    @Operation(summary = "Удаление клиента")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Создание категории")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @Operation(summary = "Получение всех категорий")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Удаление контакта")
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
