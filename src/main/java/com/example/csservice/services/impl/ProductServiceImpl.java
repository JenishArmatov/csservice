package com.example.csservice.services.impl;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.*;
import com.example.csservice.mappers.PriceMapper;
import com.example.csservice.mappers.ProductMapper;
import com.example.csservice.mappers.TagMapper;
import com.example.csservice.repository.ManufacturerRepository;
import com.example.csservice.repository.PriceRepository;
import com.example.csservice.repository.ProductRepository;
import com.example.csservice.repository.TagRepository;
import com.example.csservice.services.PriceService;
import com.example.csservice.services.ProductService;
import com.example.csservice.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductMapper productMapper;
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final PriceService priceService;
    private final TagMapper tagMapper;

    @Value("${file.upload-dir}")
    private  String uploadDir;

    @Override
    public ProductDto createProduct(ProductDto productDto, List<Image> images) {

        Product product = productMapper.toEntity(productDto, images);
       // Product newProduct = productRepository.save(product); // Получаем айди
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


        // Создаем новую цену
        Price newPrice = priceMapper.toEntity(newPriceDto, product);
        Price oldPrice = product.getCurrentPrice();
        oldPrice.setCurrent(false);
        oldPrice.setValidTo(LocalDateTime.now());
        newPrice.setProduct(product);
        newPrice.setCurrent(true);
        product.setCurrentPrice(newPrice);
        priceRepository.save(newPrice);

        // Устанавливаем новую цену как актуальную
        product.getPriceHistory().add(newPrice);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto addImageProduct(Long productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        try {
            Image productImage = FileStorageUtil.createImage(file, uploadDir);
            List<Image> images = product.getImages();
            images.add(productImage);
            product.setImages(images);
            return productMapper.toDto(productRepository.save(product));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
