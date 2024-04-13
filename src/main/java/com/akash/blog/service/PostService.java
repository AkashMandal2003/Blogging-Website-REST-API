package com.akash.blog.service;

import com.akash.blog.payload.PostDto;
import com.akash.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto,long id);

    void deletePost(long id);

    List<PostDto> getPostByCategory(Long categoryId);

    List<PostDto> searchPost(String query);
}
