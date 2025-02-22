package com.example.csservice.mappers;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.entity.Price;
import com.example.csservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public PriceDto toDto(Price price) {
        return PriceDto.builder()
                .id(price.getId())
                .productId(price.getProduct().getId())
                .priceType(price.getPriceType())
                .priceValue(price.getPriceValue())
                .build();
    }

    public Price toEntity(PriceDto dto) {
        Product product = new Product();
        product.setId(dto.getProductId());

        return Price.builder()
                .id(dto.getId())
                .product(product)
                .priceType(dto.getPriceType())
                .priceValue(dto.getPriceValue())
                .build();
    }
}
