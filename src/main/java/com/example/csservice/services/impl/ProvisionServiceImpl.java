package com.example.csservice.services.impl;

import com.example.csservice.dto.ProvisionDto;
import com.example.csservice.entity.Product;
import com.example.csservice.entity.Provision;
import com.example.csservice.mappers.ProvisionMapper;
import com.example.csservice.repository.ProvisionRepository;
import com.example.csservice.services.ProvisionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProvisionServiceImpl implements ProvisionService {

    private final ProvisionRepository provisionRepository;
    private final ProvisionMapper provisionMapper;

    @Override
    public ProvisionDto createProvision(ProvisionDto provisionDto) {

        return provisionMapper.toProvisionDto(provisionRepository.save(provisionMapper.toProvision(provisionDto)));
    }

    @Override
    public List<ProvisionDto> getAllProvisions() {
        return provisionRepository.findAll().stream()
                .map(provisionMapper::toProvisionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProvisionDto getProvisionById(Long id) {

        return provisionMapper.toProvisionDto(provisionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Услуга не найден")));
    }

    @Override
    public List<ProvisionDto> getProvisionByName(String name) {
        return provisionRepository.getProvisionByName(name).stream()
                .map(provisionMapper::toProvisionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProvisionDto updateProvision(Long id, ProvisionDto provisionDto) {
        Provision provision = provisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Услуга не найден"));
        provision.setName(provisionDto.getName());
        provision.setDescription(provisionDto.getDescription());
        provision.setPrice(provisionDto.getPrice());

        provisionRepository.save(provision);
        return provisionMapper.toProvisionDto(provision);
    }

    @Override
    public void deleteProvision(Long id) {
        provisionRepository.deleteById(id);
    }
}
