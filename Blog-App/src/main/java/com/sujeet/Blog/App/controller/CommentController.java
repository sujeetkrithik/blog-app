package com.sujeet.Blog.App.controller;

import com.sujeet.Blog.App.dto.CommentDTO;
import com.sujeet.Blog.App.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updatedComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long commentId,
                                                     @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
                                                @PathVariable("id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("comment deleted successfully", HttpStatus.OK);
    }

}
