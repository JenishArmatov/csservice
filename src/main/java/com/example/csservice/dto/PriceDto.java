package com.example.csservice.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceDto {
    private Long id;
    private Long productId;
    private String priceType;
    private Double priceValue;
}
