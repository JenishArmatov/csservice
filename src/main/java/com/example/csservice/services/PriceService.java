package com.example.csservice.services;

import com.example.csservice.dto.PriceDto;

import java.util.List;

public interface PriceService {
    PriceDto createPrice(PriceDto priceDto);
    List<PriceDto> getAllPrices();
    PriceDto getPriceById(Long id);
    void deletePrice(Long id);
}
