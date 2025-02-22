package com.example.csservice.services;

import com.example.csservice.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto);
    List<TagDto> getAllTags();
    void deleteTag(Long id);
}