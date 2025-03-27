package com.sujeet.Blog.App.service;

import com.sujeet.Blog.App.dto.PostDTO;
import com.sujeet.Blog.App.entity.Post;
import com.sujeet.Blog.App.exception.ResourceNotFoundException;
import com.sujeet.Blog.App.payload.PostResponse;
import com.sujeet.Blog.App.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert dto to entity
        Post post = mapToEntity(postDTO);
         Post post1 = postRepository.save(post);

         //convert entity to dto
        PostDTO postDTO1 = mapToDTO(post1);
        return postDTO1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

    List<Post> listOfPosts = posts.getContent();

    List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(content);
    postResponse.setPageNo(posts.getNumber());
    postResponse.setPageSize(posts.getSize());
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLast(posts.isLast());
    return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
       return  mapToDTO(post);
    }


    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    //convert entity to dto
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }

}
