package com.example.csservice.controller;

import com.example.csservice.dto.ProvisionDto;
import com.example.csservice.services.ProvisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provisions")
@RequiredArgsConstructor
public class ProvisionController {
    private final ProvisionService provisionService;

    @GetMapping
    public ResponseEntity<List<ProvisionDto>> getAllProducts() {
        return ResponseEntity.ok(provisionService.getAllProvisions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvisionDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(provisionService.getProvisionById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProvisionDto>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(provisionService.getProvisionByName(name));
    }

}
