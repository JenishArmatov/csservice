package com.example.csservice.controller;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Image;
import com.example.csservice.services.ImageService;
import com.example.csservice.services.PriceService;
import com.example.csservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final PriceService priceService;

    // 🔹 Создание товара с изображениями
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            @RequestParam("files") List<MultipartFile> files) {

        List<Image> images = files.stream()
                .map(imageService::createImage)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productService.createProduct(productDto, images));
    }

    // 🔹 Получение всех товаров
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 🔹 Получение товара по ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 🔹 Поиск товара по имени
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    // 🔹 Обновление товара
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    // 🔹 Обновление цены товара
    @PutMapping("/update/price/{id}")
    public ResponseEntity<ProductDto> updateProductPrice(@PathVariable Long id, @RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(productService.updateProductPrice(id, priceDto));
    }

    // 🔹 Удаление товара
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Добавление цены к товару
    @PostMapping("/{id}/prices")
    public ResponseEntity<PriceDto> addPriceToProduct(@RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(priceService.createPrice(priceDto));
    }

    // 🔹 Удаление цены
    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}

