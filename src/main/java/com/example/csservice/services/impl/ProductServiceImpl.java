package com.example.csservice.services.impl;

import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Product;
import com.example.csservice.mappers.ProductMapper;
import com.example.csservice.repository.ManufacturerRepository;
import com.example.csservice.repository.ProductRepository;
import com.example.csservice.repository.TagRepository;
import com.example.csservice.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final TagRepository tagRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProductByName(String productName) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        productMapper.updateEntity(productDto, product);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
