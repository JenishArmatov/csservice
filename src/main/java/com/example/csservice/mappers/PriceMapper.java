package com.example.csservice.mappers;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.entity.Price;
import com.example.csservice.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceMapper {

    public PriceDto toDto(Price price) {
        if (price == null) {
            return null;
        }

        return PriceDto.builder()
                .id(price.getId())
                .productId(price.getProduct().getId())
                .priceType(price.getPriceType())
                .priceValue(price.getPriceValue())
                .isCurrent(price.isCurrent()) // Флаг актуальной цены
                .validFrom(price.getValidFrom())
                .validTo(price.getValidTo())
                .build();
    }

    public Price toEntity(PriceDto dto, Product product) {
        if (dto == null || product == null) {
            return null;
        }

        return Price.builder()
                .id(dto.getId())
                .product(product)
                .priceType(dto.getPriceType())
                .priceValue(dto.getPriceValue())
                .isCurrent(dto.isCurrent()) // Установка флага актуальности
                .validFrom(dto.getValidFrom() != null ? dto.getValidFrom() : LocalDateTime.now()) // Если нет даты, ставим текущую
                .validTo(dto.getValidTo()) // Может быть null
                .build();
    }

    public List<PriceDto> toDtoList(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return Collections.emptyList();
        }
        return prices.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Price> toEntityList(List<PriceDto> dtos, Product product) {
        if (dtos == null || dtos.isEmpty() || product == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(dto -> toEntity(dto, product)).collect(Collectors.toList());
    }
}

