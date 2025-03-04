package com.example.csservice.services.impl;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Image;
import com.example.csservice.entity.Manufacturer;
import com.example.csservice.entity.Price;
import com.example.csservice.entity.Product;
import com.example.csservice.mappers.PriceMapper;
import com.example.csservice.mappers.ProductMapper;
import com.example.csservice.repository.ManufacturerRepository;
import com.example.csservice.repository.PriceRepository;
import com.example.csservice.repository.ProductRepository;
import com.example.csservice.repository.TagRepository;
import com.example.csservice.services.PriceService;
import com.example.csservice.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final TagRepository tagRepository;
    private final ProductMapper productMapper;
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto, List<Image> images) {
        Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                .orElseGet(() -> {
                    Manufacturer newManufacturer = Manufacturer.builder()
                            .manufactureText(productDto.getManufacturerName())
                            .build();
                    return manufacturerRepository.save(newManufacturer);
                });

        Product product = productMapper.toEntity(productDto, images);
        product.setManufacturer(manufacturer);
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
    public ProductDto updateProductPrice(Long productId, PriceDto newPriceDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));

        // Делаем текущую цену устаревшей
//        if (product.getCurrentPrice() != null) {
//            Price oldPrice = product.getCurrentPrice();
//            oldPrice.setCurrent(false);
//            oldPrice.setValidTo(LocalDateTime.now());
//        }

        // Создаем новую цену
        Price newPrice = priceMapper.toEntity(newPriceDto, product);
        Price oldPrice = product.getCurrentPrice();
        oldPrice.setCurrent(false);
        oldPrice.setValidTo(LocalDateTime.now());
        newPrice.setProduct(product);
        newPrice.setCurrent(true);
        priceRepository.save(newPrice);

        // Устанавливаем новую цену как актуальную
        product.setCurrentPrice(newPrice);
        product.getPriceHistory().add(newPrice);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
