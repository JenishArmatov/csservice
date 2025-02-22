package com.example.csservice.mappers;

import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }

    public Tag toEntity(TagDto dto) {
        return Tag.builder()
                .id(dto.getId())
                .tagName(dto.getTagName())
                .build();
    }
}
