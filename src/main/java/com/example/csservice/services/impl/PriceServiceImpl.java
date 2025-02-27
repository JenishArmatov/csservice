package com.example.csservice.services.impl;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.entity.Price;
import com.example.csservice.entity.Product;
import com.example.csservice.mappers.PriceMapper;
import com.example.csservice.repository.PriceRepository;
import com.example.csservice.repository.ProductRepository;
import com.example.csservice.services.PriceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final ProductRepository productRepository;

    @Override
    public PriceDto createPrice(PriceDto priceDto) {
        Product product = productRepository.findById(priceDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Товар с ID " + priceDto.getProductId() + " не найден"));
        Price price = priceMapper.toEntity(priceDto, product);
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
    public PriceDto updatePrice(Long id, PriceDto priceDto) {
        Product product = productRepository.findById(priceDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Товар с ID " + priceDto.getProductId() + " не найден"));
        Price oldPrice = priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Цена не найдена"));
        oldPrice.setProduct(product);
        oldPrice.setPriceType(priceDto.getPriceType());
        oldPrice.setPriceValue(priceDto.getPriceValue());
        oldPrice.setCurrent(priceDto.isCurrent());
        oldPrice.setValidFrom(priceDto.getValidFrom() != null ? priceDto.getValidFrom() : LocalDateTime.now());
        oldPrice.setValidTo(priceDto.getValidTo());

        return priceMapper.toDto(priceRepository.save(oldPrice));
    }

    @Override
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }
}
