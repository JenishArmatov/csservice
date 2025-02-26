package com.example.csservice.services;

import com.example.csservice.dto.ContactDto;
import com.example.csservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactService {
    ContactDto createContact(ContactDto contactDto, MultipartFile file);
    List<ContactDto> getAllContacts();
    ContactDto getContactById(Long id);
    List<ContactDto>  getContactByName(String name);
    ContactDto updateContact(Long id, ContactDto contactDto);
    void deleteContact(Long id);
}
