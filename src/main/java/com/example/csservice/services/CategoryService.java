package com.example.csservice.services;

import com.example.csservice.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    List<CategoryDto>  getCategoryByName(String name);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);
}
