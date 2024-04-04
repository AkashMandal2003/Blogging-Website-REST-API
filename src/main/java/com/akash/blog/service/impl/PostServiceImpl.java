package com.akash.blog.service.impl;

import com.akash.blog.entity.Category;
import com.akash.blog.entity.Post;
import com.akash.blog.exception.ResourceNotFoundException;
import com.akash.blog.payload.PostDto;
import com.akash.blog.payload.PostResponse;
import com.akash.blog.repository.CategoryRepository;
import com.akash.blog.repository.PostRepository;
import com.akash.blog.service.PostService;
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
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    //convert entity into DTO
    private PostDto mapToDTO(Post post){
        //PostDto postDto=new PostDto();
        //postDto.setId(post.getId());
        //postDto.setTitle(post.getTitle());
        //postDto.setDescription(post.getDescription());
        //postDto.setContent(post.getContent());

        PostDto postDto=mapper.map(post,PostDto.class);
        return postDto;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        //Post post=new Post();
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());

        Post post=mapper.map(postDto,Post.class);
        return post;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        //convert DTO to entity
        Post post=mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //convert entity to DTO
        PostDto postResponse=mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPost=posts.getContent();
        List<PostDto> content = listOfPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        //customize pagination
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from the database
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost=postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts=postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post)->mapToDTO(post)).collect(Collectors.toList());
    }
}
