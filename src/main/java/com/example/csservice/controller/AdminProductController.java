package com.example.csservice.controller;

import com.example.csservice.dto.ImageDto;
import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Image;
import com.example.csservice.services.ImageService;
import com.example.csservice.services.PriceService;
import com.example.csservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin Product Controller", description = "Управление товарами в панели администратора")
public class AdminProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final PriceService priceService;

    @Operation(summary = "Создание товара с изображениями")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(
            @RequestPart("productDto") @Valid ProductDto productDto,
            @RequestPart("files") List<MultipartFile> files) {
        List<Image> images = files.stream()
                .map(imageService::createImage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productService.createProduct(productDto, images));
    }

    @Operation(summary = "Получение всех товаров")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Получение товара по ID")
    @GetMapping("/search-id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Поиск товара по имени")
    @GetMapping("/search-name/{name}")
    public ResponseEntity<List<ProductDto>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @Operation(summary = "Обновление товара")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @Operation(summary = "Обновление цены товара")
    @PutMapping("/update/price/{id}")
    public ResponseEntity<ProductDto> updateProductPrice(@PathVariable Long id, @RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(productService.updateProductPrice(id, priceDto));
    }

    @Operation(summary = "Удаление товара")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Добавление цены к товару")
    @PostMapping("/{id}/prices")
    public ResponseEntity<PriceDto> addPriceToProduct(@RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(priceService.createPrice(priceDto));
    }

    @Operation(summary = "Удаление цены")
    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удаление изображения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Изображение успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено")
    })
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновление изображения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно обновлено",
                    content = @Content(schema = @Schema(implementation = ImageDto.class))),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено")
    })
    @PutMapping(value = "/images/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> updateImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.updateImage(id, file));
    }
}