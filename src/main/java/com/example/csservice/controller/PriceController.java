package com.example.csservice.controller;

import com.example.csservice.dto.PriceDto;
import com.example.csservice.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    public ResponseEntity<PriceDto> createPrice(@RequestBody PriceDto priceDto) {
        return ResponseEntity.ok(priceService.createPrice(priceDto));
    }

    @GetMapping
    public ResponseEntity<List<PriceDto>> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceDto> getPriceById(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.getPriceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
