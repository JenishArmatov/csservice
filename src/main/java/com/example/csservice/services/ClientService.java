package com.example.csservice.services;

import com.example.csservice.dto.ClientDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto, MultipartFile file);
    List<ClientDto> getAllClients();
    ClientDto getClientById(Long id);
    ClientDto updateClient(Long id, ClientDto clientDto);
    void deleteClient(Long id);

    ClientDto getClientByName(String name);
}