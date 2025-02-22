package com.example.csservice.controller;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto,
                                                  @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(clientService.createClient(clientDto, file));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }
    @GetMapping("/{name}")
    public ResponseEntity<ClientDto> getClientByName(@PathVariable String name) {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
