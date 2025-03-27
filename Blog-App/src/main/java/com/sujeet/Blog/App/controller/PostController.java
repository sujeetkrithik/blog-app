package com.sujeet.Blog.App.controller;

import com.sujeet.Blog.App.dto.PostDTO;
import com.sujeet.Blog.App.payload.PostResponse;
import com.sujeet.Blog.App.service.PostService;
import com.sujeet.Blog.App.utils.PostConstant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
       return new ResponseEntity<>(postService.createPost(postDTO),HttpStatus.CREATED);
    }
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = PostConstant.DEFAULT_PAGE_NO, required = false)int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = PostConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = PostConstant.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = PostConstant.DEFAULT_SORT_DIR, required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") long id){
        return new ResponseEntity<>(postService.updatePost(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
