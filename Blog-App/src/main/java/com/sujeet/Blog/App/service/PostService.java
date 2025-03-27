package com.sujeet.Blog.App.service;

import com.sujeet.Blog.App.dto.PostDTO;
import com.sujeet.Blog.App.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(long id);
    PostDTO updatePost(PostDTO postDTO, long id);
    void deletePostById(long id);

}
