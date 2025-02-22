package com.example.csservice.mappers;

import com.example.csservice.dto.ManufacturerDto;
import com.example.csservice.entity.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper {
    public ManufacturerDto toDto(Manufacturer manufacturer) {
        return ManufacturerDto.builder()
                .id(manufacturer.getId())
                .manufactureText(manufacturer.getManufactureText())
                .build();
    }

    public Manufacturer toEntity(ManufacturerDto dto) {
        return Manufacturer.builder()
                .id(dto.getId())
                .manufactureText(dto.getManufactureText())
                .build();
    }
}
