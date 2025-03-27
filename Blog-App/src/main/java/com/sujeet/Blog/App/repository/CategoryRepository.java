package com.sujeet.Blog.App.repository;

import com.sujeet.Blog.App.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}