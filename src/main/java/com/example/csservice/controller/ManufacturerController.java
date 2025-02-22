package com.example.csservice.controller;

import com.example.csservice.dto.ManufacturerDto;
import com.example.csservice.services.ManufacturerService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ManufacturerDto> createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.createManufacturer(manufacturerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDto> updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerDto manufacturerDto) {
        manufacturerDto.setId(id);
        return ResponseEntity.ok(manufacturerService.updateManufacturer(manufacturerDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDto> getManufacturerById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ManufacturerDto> getManufacturerByName(@PathVariable String name) {
        return ResponseEntity.ok(manufacturerService.getManufacturerByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
