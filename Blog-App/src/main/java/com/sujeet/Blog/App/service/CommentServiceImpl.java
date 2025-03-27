package com.sujeet.Blog.App.service;

import com.sujeet.Blog.App.dto.CommentDTO;
import com.sujeet.Blog.App.entity.Comment;
import com.sujeet.Blog.App.entity.Post;
import com.sujeet.Blog.App.exception.ApiException;
import com.sujeet.Blog.App.exception.ResourceNotFoundException;
import com.sujeet.Blog.App.repository.CommentRepository;
import com.sujeet.Blog.App.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
       Comment comment  = mapToEntity(commentDTO);
       Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post",
               "id", postId));
       comment.setPost(post);

       Comment comment1 = commentRepository.save(comment);
       return mapToDTO(comment1);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(
                "comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post",
                "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
        new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

       Comment updatedComment =  commentRepository.save(comment);
       return mapToDTO(updatedComment);
    }
    @Override
    public void deleteComment(Long postId, Long commentId){
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
        }
        commentRepository.delete(comment);
    }


    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
