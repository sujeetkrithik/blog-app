package com.sujeet.Blog.App.service;

import com.sujeet.Blog.App.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(Long categoryId);
    List<CategoryDTO> getAllCategories();
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto);
    void deleteCategoryById(Long categoryId);
}
