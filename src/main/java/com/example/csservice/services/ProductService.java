package com.example.csservice.services;

import com.example.csservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    List<ProductDto> getProductByName(String productName);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}