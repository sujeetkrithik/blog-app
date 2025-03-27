package com.sujeet.Blog.App.controller;


import com.sujeet.Blog.App.dto.CategoryDTO;
import com.sujeet.Blog.App.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Add Category
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Get Category by id
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    // Get all Categories
    @GetMapping
    public  ResponseEntity<List<CategoryDTO>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Update Category by id
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable(name = "categoryId") Long id){
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete Category by id
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "categoryId") Long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}