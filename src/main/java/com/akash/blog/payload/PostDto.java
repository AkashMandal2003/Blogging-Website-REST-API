package com.akash.blog.payload;

import com.akash.blog.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<Comment> comments;
}
