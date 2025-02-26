package com.example.csservice.services.impl;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.entity.Client;
import com.example.csservice.entity.Image;
import com.example.csservice.mappers.ClientMapper;
import com.example.csservice.repository.ClientRepository;
import com.example.csservice.services.ClientService;
import com.example.csservice.services.ImageService;
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
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ImageService imageService;
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public ClientDto createClient(ClientDto clientDto, MultipartFile file) {

        Image image = imageService.createImage(file);
        if (image != null){
            Client client = clientMapper.toEntity(clientDto, image);
            return clientMapper.toDto(clientRepository.save(client));
        }
        else return null;

    }


    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиент с ID " + id + " не найден"));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиент с ID " + id + " не найден"));

        client.setNameClient(clientDto.getNameClient());
   //     client.setImage(clientMapper.toEntity(clientDto.));

        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Клиент с ID " + id + " не найден");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public List<ClientDto> getClientByName(String name) {
        return clientRepository.findByNameClient(name).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }
}
