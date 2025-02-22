package com.example.csservice.services.impl;

import com.example.csservice.dto.ContactDto;
import com.example.csservice.entity.Contact;
import com.example.csservice.entity.Image;
import com.example.csservice.mappers.ContactMapper;
import com.example.csservice.repository.ContactRepository;
import com.example.csservice.services.ContactService;
import com.example.csservice.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public ContactDto createContact(ContactDto contactDto, MultipartFile file) {
        Image image = null;
        try {
            image = FileStorageUtil.createImage(file, uploadDir);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (image != null){
            Contact contact = contactMapper.toEntity(contactDto, image);
            return contactMapper.toDto(contactRepository.save(contact));
        }
        else return null;

    }


    @Override
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Контакт с ID " + id + " не найден"));
        return contactMapper.toDto(contact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Контакт с ID " + id + " не найден"));

        return contactMapper.toDto(contactRepository.save(contact));
    }

    @Override
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new EntityNotFoundException("Контакт с ID " + id + " не найден");
        }
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDto> getContactByName(String name) {
        return contactRepository.getContactByName(name).stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }
}
