package com.akash.blog.payload;

import com.akash.blog.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private long id;
    //title should not be not null or empty
    //title should have at least 2 characters
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    //description should not be not null or empty
    //description should have at least 10 characters
    @NotEmpty(message = "Description should not be empty")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    //content should not be not null or empty
    @NotEmpty(message = "Content should not be empty")
    private String content;

    private Set<Comment> comments;

    private Long categoryId;

}
