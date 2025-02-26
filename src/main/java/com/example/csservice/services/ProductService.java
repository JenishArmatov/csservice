package com.example.csservice.services;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Image;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, List<Image> images);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    List<ProductDto> getProductByName(String productName);
    ProductDto updateProduct(Long id, ProductDto productDto);
    ProductDto updateProductPrice(Long productId, PriceDto newPriceDto);
    void deleteProduct(Long id);
}