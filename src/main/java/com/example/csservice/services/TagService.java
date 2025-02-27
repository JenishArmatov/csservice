package com.example.csservice.services;

import com.example.csservice.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto);
    List<TagDto> getAllTags();
    List<TagDto> getTagsByTagName(String tagName);
    TagDto getTagById(long id);
    TagDto updateTag(Long id, TagDto tagDto);
    void deleteTag(Long id);
}