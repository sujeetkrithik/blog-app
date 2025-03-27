package com.sujeet.Blog.App.repository;

import com.sujeet.Blog.App.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

