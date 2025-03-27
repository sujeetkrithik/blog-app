package com.sujeet.Blog.App.service;


import com.sujeet.Blog.App.dto.CategoryDTO;
import com.sujeet.Blog.App.entity.Category;
import com.sujeet.Blog.App.exception.ResourceNotFoundException;
import com.sujeet.Blog.App.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return mapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        categoryRepository.delete(category);
    }
}
