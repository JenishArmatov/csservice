package com.example.csservice.services.impl;

import com.example.csservice.dto.ManufacturerDto;
import com.example.csservice.entity.Manufacturer;
import com.example.csservice.mappers.ManufacturerMapper;
import com.example.csservice.repository.ManufacturerRepository;
import com.example.csservice.services.ManufacturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDto);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.toDto(savedManufacturer);
    }

    @Override
    public ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer existingManufacturer = manufacturerRepository.findById(manufacturerDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Производитель не найден"));

        existingManufacturer.setManufactureText(manufacturerDto.getManufactureText());
        Manufacturer updatedManufacturer = manufacturerRepository.save(existingManufacturer);
        return manufacturerMapper.toDto(updatedManufacturer);
    }

    @Override
    public ManufacturerDto getManufacturerById(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Производитель не найден"));
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    public ManufacturerDto getManufacturerByName(String manufacturerName) {
        Manufacturer manufacturer = manufacturerRepository.findByManufactureText(manufacturerName)
                .orElseThrow(() -> new EntityNotFoundException("Производитель с таким именем не найден"));
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    public List<ManufacturerDto> getAllManufacturers() {
        return manufacturerRepository.findAll().stream()
                .map(manufacturerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteManufacturer(Long manufacturerId) {
        if (!manufacturerRepository.existsById(manufacturerId)) {
            throw new EntityNotFoundException("Производитель не найден");
        }
        manufacturerRepository.deleteById(manufacturerId);
    }
}

