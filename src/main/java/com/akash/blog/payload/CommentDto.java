package com.akash.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    //name should not be not null or empty
    @NotEmpty(message = "Name should not be empty")
    private String name;

    //email should not be not null or empty
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;


    //body should not be not null or empty
    @NotEmpty(message = "Message body should not be empty")
    @Size(min = 10, message = "Message body should have at least 10 characters")
    private String body;

}
