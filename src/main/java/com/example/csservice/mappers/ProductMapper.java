package com.example.csservice.mappers;

import com.example.csservice.dto.ProductDto;
import com.example.csservice.entity.Manufacturer;
import com.example.csservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .article(product.getArticle())
                .count(product.getCount())
                .manufacturerId(product.getManufacturer().getId())
                .build();
    }

    public Product toEntity(ProductDto dto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(dto.getManufacturerId());

        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .article(dto.getArticle())
                .count(dto.getCount())
                .manufacturer(manufacturer)
                .build();
    }

    public void updateEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setArticle(dto.getArticle());
        product.setCount(dto.getCount());
    }
}
