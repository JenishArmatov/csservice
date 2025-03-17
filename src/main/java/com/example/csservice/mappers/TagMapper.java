package com.example.csservice.mappers;

import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagMapper {

    public  TagDto toDto(Tag tag) {
        if (tag == null) {
            return null;
        }

        return TagDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
              //  .products(tag.getProducts()) // если нужно передавать связанные продукты
                .build();
    }

    public Tag toEntity(TagDto tagDto) {
        if (tagDto == null) {
            return null;
        }

        return Tag.builder()
                .id(tagDto.getId())
                .tagName(tagDto.getTagName())
              //  .products(tagDto.getProducts()) // если нужно передавать связанные продукты
                .build();
    }

    public Set<TagDto> toDtoSet(Set<Tag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }

        return tags.stream().map(this::toDto).collect(Collectors.toSet());
    }

    public Set<Tag> toEntitySet(Set<TagDto> tagDtos) {
        if (tagDtos == null) {
            return new HashSet<>();
        }

        return tagDtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
