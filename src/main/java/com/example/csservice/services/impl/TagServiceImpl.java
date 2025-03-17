package com.example.csservice.services.impl;

import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.Tag;
import com.example.csservice.mappers.TagMapper;
import com.example.csservice.repository.TagRepository;
import com.example.csservice.services.TagService;
import jakarta.persistence.EntityNotFoundException;
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
    public List<TagDto> getTagsByTagName(String tagName) {
        return tagRepository.findByTagName(tagName).stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(long id) {
        return tagMapper.toDto(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тэг с ID " + id + " не найден"));
        tag.setTagName(tagDto.getTagName());
       // tag.setProducts(tagDto.getProducts());

        return tagMapper.toDto(tagRepository.save(tagMapper.toEntity(tagDto)));
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}