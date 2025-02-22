package com.example.csservice.mappers;

import com.example.csservice.dto.CategoryDto;
import com.example.csservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setUrl(categoryDto.getUrl());
        category.setCategoryName(categoryDto.getContentType());
        return category;
    }

    public CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setUrl(category.getUrl());
        categoryDto.setContentType(category.getCategoryName());
        return categoryDto;
    }
}
