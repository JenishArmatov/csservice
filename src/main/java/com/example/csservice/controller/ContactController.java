package com.example.csservice.controller;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ContactDto;
import com.example.csservice.entity.Image;
import com.example.csservice.services.ClientService;
import com.example.csservice.services.ContactService;
import com.example.csservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }
    @GetMapping("/{name}")
    public ResponseEntity<List<ContactDto>> getContactByName(@PathVariable String name) {
        return ResponseEntity.ok(contactService.getContactByName(name));
    }
}
