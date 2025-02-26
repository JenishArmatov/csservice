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


}
