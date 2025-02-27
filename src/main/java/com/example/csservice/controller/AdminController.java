package com.example.csservice.controller;

import com.example.csservice.dto.*;
import com.example.csservice.entity.Image;
import com.example.csservice.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin Controller", description = "API для управления административными функциями")
public class AdminController {

    private final BasketService basketService;
    private final ClientService clientService;
    private final ProvisionService provisionService;
    private final PriceService priceService;
    private final CategoryService categoryService;
    private final ContactService contactService;
    private final ManufacturerService manufacturerService;
    private final TagService tagService;

    @Operation(summary = "Добавить товар в корзину", description = "Добавляет товар в корзину пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар добавлен в корзину",
                    content = @Content(schema = @Schema(implementation = BasketDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка добавления товара")
    })
    @PostMapping("/basket/add")
    public ResponseEntity<BasketDto> addItemToBasket(@RequestParam Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        return ResponseEntity.ok(basketService.addItemToBasket(userId, productId, quantity));
    }

    @Operation(summary = "Удалить товар из корзины", description = "Удаляет указанный товар из корзины пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар удален из корзины",
                    content = @Content(schema = @Schema(implementation = BasketDto.class))),
            @ApiResponse(responseCode = "404", description = "Товар или пользователь не найден")
    })
    @DeleteMapping("/basket/remove-item")
    public ResponseEntity<BasketDto> removeItemFromBasket(@RequestParam Long userId,
                                                          @RequestParam Long productId) {
        return ResponseEntity.ok(basketService.removeItemFromBasket(userId, productId));
    }

    @Operation(summary = "Очистить корзину", description = "Очищает всю корзину пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Корзина очищена"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/basket/clear/{userId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long userId) {
        basketService.clearBasket(userId);
        return ResponseEntity.noContent().build();
    }

    // Категории
    @Operation(summary = "Создать категорию", description = "Добавляет новую категорию.")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @Operation(summary = "Обновить категорию", description = "Обновляет информацию о категории.")
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Parameter(description = "ID категории") @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
    }

    @Operation(summary = "Удалить категорию", description = "Удаляет категорию по её ID.")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Клиенты
    @Operation(summary = "Создать клиента", description = "Добавляет нового клиента.")
    @PostMapping("/clients")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto,
                                                  @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(clientService.createClient(clientDto, file));
    }

    @Operation(summary = "Обновить клиента", description = "Обновляет данные клиента.")
    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDto));
    }

    @Operation(summary = "Удалить клиента", description = "Удаляет клиента по ID.")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // Контакты
    @Operation(summary = "Создать контакт", description = "Добавляет новый контакт.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакт успешно создан",
                    content = @Content(schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping(value = "/contacts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContactDto> createContact(
            @RequestPart("contactDto") @Parameter(description = "Данные контакта", required = true) ContactDto contactDto,
            @RequestPart("file") @Parameter(description = "Файл изображения", required = true, content = @Content(mediaType = "multipart/form-data"))
            MultipartFile file) {
        return ResponseEntity.ok(contactService.createContact(contactDto, file));
    }

    @Operation(
            summary = "Обновить контакт",
            description = "Обновляет данные контакта по ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Контакт успешно обновлен",
                    content = @Content(schema = @Schema(implementation = ContactDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации данных"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контакт не найден"
            )
    })
    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactDto> updateContact(
            @Parameter(description = "ID контакта", required = true, example = "1")
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновленные данные контакта",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContactDto.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody ContactDto contactDto
    )
    {
        return ResponseEntity.ok(contactService.updateContact(id, contactDto));
    }

    @Operation(summary = "Удалить контакт", description = "Удаляет контакт по ID.")
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // Производители
    @Operation(summary = "Создать производителя", description = "Добавляет нового производителя.")
    @PostMapping("/manufacturers")
    public ResponseEntity<ManufacturerDto> createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.createManufacturer(manufacturerDto));
    }

    @Operation(summary = "Обновить производителя", description = "Обновляет информацию о производителе.")
    @PutMapping("/manufacturers/{id}")
    public ResponseEntity<ManufacturerDto> updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerDto manufacturerDto) {
        manufacturerDto.setId(id);
        return ResponseEntity.ok(manufacturerService.updateManufacturer(manufacturerDto));
    }

    @Operation(summary = "Удалить производителя", description = "Удаляет производителя по ID.")
    @DeleteMapping("/manufacturers/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }

    // Цены
    @Operation(summary = "Создать цену", description = "Добавляет новую цену для товара.")
    @PostMapping("/prices")
    public ResponseEntity<PriceDto> createPrice(@RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(priceService.createPrice(priceDto));
    }

    @Operation(summary = "Обновить цену", description = "Обновляет информацию о цене товара.")
    @PutMapping("/prices/{id}")
    public ResponseEntity<PriceDto> updatePrice(@PathVariable Long id, @RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(priceService.updatePrice(id, priceDto));
    }

    @Operation(summary = "Удалить цену", description = "Удаляет цену по её ID.")
    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    // Поставки
    @Operation(summary = "Создать услугу", description = "Добавляет новую услугу.")
    @PostMapping("/provisions")
    public ResponseEntity<ProvisionDto> createProvision(@RequestBody ProvisionDto provisionDto) {
        return ResponseEntity.ok(provisionService.createProvision(provisionDto));
    }

    @Operation(summary = "Обновить услугу", description = "Обновляет данные услуг.")
    @PutMapping("/provisions/{id}")
    public ResponseEntity<ProvisionDto> updateProvision(@PathVariable Long id, @RequestBody ProvisionDto provisionDto) {
        return ResponseEntity.ok(provisionService.updateProvision(id, provisionDto));
    }

    @Operation(summary = "Удалить услугу", description = "Удаляет услугу по ID.")
    @DeleteMapping("/provisions/{id}")
    public ResponseEntity<Void> deleteProvision(@PathVariable Long id) {
        provisionService.deleteProvision(id);
        return ResponseEntity.noContent().build();
    }

    // Теги
    @Operation(summary = "Создать тег", description = "Добавляет новый тег.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег успешно создан",
                    content = @Content(schema = @Schema(implementation = TagDto.class))),
            @ApiResponse(responseCode = "404", description = "Тег не создан")
    })
    @PostMapping("/tags")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @Operation(summary = "Обновить тег", description = "Обновляет информацию о теге.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег успешно обновлен",
                    content = @Content(schema = @Schema(implementation = TagDto.class))),
            @ApiResponse(responseCode = "404", description = "Тег не найден")
    })
    @PutMapping("/tags/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.updateTag(id, tagDto));
    }

    @Operation(summary = "Удалить тег", description = "Удаляет тег по ID.")
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}

