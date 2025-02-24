package com.example.csservice.services;

import com.example.csservice.dto.ProvisionDto;

import java.util.List;

public interface ProvisionService {
    ProvisionDto createProvision(ProvisionDto provisionDto);
    List<ProvisionDto> getAllProvisions();
    ProvisionDto getProvisionById(Long id);
    List<ProvisionDto> getProvisionByName(String name);
    ProvisionDto updateProvision(Long id, ProvisionDto provisionDto);
    void deleteProvision(Long id);
}