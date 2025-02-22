package com.example.csservice.services;

import com.example.csservice.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {
    ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);
    ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto);
    ManufacturerDto getManufacturerById(Long manufacturerId);
    ManufacturerDto getManufacturerByName(String manufacturerName);
    List<ManufacturerDto> getAllManufacturers();
    void deleteManufacturer(Long manufacturerId);
}
