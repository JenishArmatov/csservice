package com.example.csservice.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String article; // Артикул
    private Integer count; // Количество на складе
    private Long manufacturerId; // ID производителя
    private String manufacturerName; // Название производителя
    private Set<TagDto> tags; // Названия тегов
    private PriceDto price;
    private List<PriceDto> prices; // Список цен
    private List<ImageDto> images; // Список изображений
}