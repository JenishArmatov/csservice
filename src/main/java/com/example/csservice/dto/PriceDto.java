package com.example.csservice.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private Long id;

    private Long productId; // ID Товара

    private String priceType; // Тип цены (например, "Оптовая", "Розничная")

    private Double priceValue; // Значение цены

    private boolean current; // Флаг: актуальная цена

    private LocalDateTime validFrom; // Дата начала действия цены

    private LocalDateTime validTo; // Дата окончания действия (null, если активна)

}
