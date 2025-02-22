package com.example.csservice.controller;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ContactDto;
import com.example.csservice.services.ClientService;
import com.example.csservice.services.ContactService;
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

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto,
                                                  @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(contactService.createContact(contactDto, file));
    }

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
    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateClient(@PathVariable Long id, @RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(contactService.updateContact(id, contactDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
