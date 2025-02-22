package com.example.csservice.services.impl;


import com.example.csservice.dto.CategoryDto;
import com.example.csservice.entity.Category;
import com.example.csservice.mappers.CategoryMapper;
import com.example.csservice.repository.CategoryRepository;
import com.example.csservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        category = categoryRepository.save(category);
        CategoryDto result = categoryMapper.toCategoryDto(category);

        return result;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(categoryMapper.toCategoryDto(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategoryByName(String name) {
        List<Category> categories = categoryRepository.findByCategoryName(name);
        if (categories.isEmpty()) {
            return null;
        }
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(categoryMapper.toCategoryDto(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        Category newCategory = categoryMapper.toCategory(categoryDto);
        Category updatedCategory = categoryRepository.save(newCategory);
        return categoryMapper.toCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
