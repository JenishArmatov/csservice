package com.example.csservice.mappers;

import com.example.csservice.dto.ProvisionDto;
import com.example.csservice.entity.Provision;
import org.springframework.stereotype.Component;

@Component
public class ProvisionMapper {

    public Provision toProvision(ProvisionDto provisionDto) {
        Provision provision = new Provision();
        provision.setId(provisionDto.getId());
        provision.setName(provisionDto.getName());
        provision.setDescription(provisionDto.getDescription());
        provision.setPrice(provisionDto.getPrice());
        return provision;
    }

    public ProvisionDto toProvisionDto(Provision provision) {
        ProvisionDto provisionDto = new ProvisionDto();
        provisionDto.setId(provision.getId());
        provisionDto.setName(provision.getName());
        provisionDto.setDescription(provision.getDescription());
        provisionDto.setPrice(provision.getPrice());
        return provisionDto;
    }
}
