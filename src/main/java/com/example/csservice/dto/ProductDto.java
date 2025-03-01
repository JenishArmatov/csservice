package com.example.csservice.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String article; // Артикул
    private Integer count; // Количество на складе
    private Long manufacturerId; // ID производителя
    private String manufacturerName; // Название производителя
    private Set<String> tags; // Названия тегов
    private PriceDto price;
    private List<PriceDto> prices; // Список цен
    private List<ImageDto> images; // Список изображений
}