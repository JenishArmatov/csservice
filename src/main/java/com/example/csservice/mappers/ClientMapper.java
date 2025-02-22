package com.example.csservice.mappers;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.entity.Client;
import com.example.csservice.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }

        return ClientDto.builder()
                .id(client.getId())
                .nameClient(client.getNameClient())
                .imageId(client.getImage() != null ? client.getImage().getId() : null)
                .build();
    }

    public Client toEntity(ClientDto clientDto, Image image) {
        if (clientDto == null) {
            return null;
        }

        Client client = new Client();
       // client.setId(clientDto.getId());
        client.setNameClient(clientDto.getNameClient());
        client.setImage(image); // Связываем с изображением

        return client;
    }
}
