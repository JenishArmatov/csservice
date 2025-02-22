package com.example.csservice.services.impl;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.entity.Price;
import com.example.csservice.mappers.PriceMapper;
import com.example.csservice.repository.PriceRepository;
import com.example.csservice.services.PriceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public PriceDto createPrice(PriceDto priceDto) {
        Price price = priceMapper.toEntity(priceDto);
        priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    @Override
    public List<PriceDto> getAllPrices() {
        return priceRepository.findAll().stream()
                .map(priceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDto getPriceById(Long id) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Цена не найдена"));
        return priceMapper.toDto(price);
    }

    @Override
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }
}
