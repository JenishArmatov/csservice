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

    @PostMapping
    public ResponseEntity<ProvisionDto> createProvision(@RequestBody ProvisionDto provisionDto) {
        return ResponseEntity.ok(provisionService.createProvision(provisionDto));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<ProvisionDto> updateProduct(@PathVariable Long id, @RequestBody ProvisionDto provisionDto) {
        return ResponseEntity.ok(provisionService.updateProvision(id, provisionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        provisionService.deleteProvision(id);
        return ResponseEntity.noContent().build();
    }
}
