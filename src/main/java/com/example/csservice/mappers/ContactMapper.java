package com.example.csservice.mappers;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ContactDto;
import com.example.csservice.entity.Client;
import com.example.csservice.entity.Contact;
import com.example.csservice.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactDto toDto(Contact contact) {
        if (contact == null) {
            return null;
        }

        return ContactDto.builder()
                .id(contact.getId())
                .link(contact.getLink())
                .name(contact.getName())
                .imageId(contact.getImage().getId()).build();
    }

    public Contact toEntity(ContactDto contactDto, Image image) {
        if (contactDto == null) {
            return null;
        }

        Contact contact = new Contact();
        // contact.setId(contactDto.getId());
        contact.setName(contactDto.getName());
        contact.setLink(contactDto.getLink());
        contact.setImage(image);

        return contact;
    }
}
