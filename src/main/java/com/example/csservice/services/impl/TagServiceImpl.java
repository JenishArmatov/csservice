package com.example.csservice.services.impl;

import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.Tag;
import com.example.csservice.mappers.TagMapper;
import com.example.csservice.repository.TagRepository;
import com.example.csservice.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagMapper.toEntity(tagDto);
        tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}