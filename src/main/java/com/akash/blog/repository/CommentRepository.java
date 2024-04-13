package com.akash.blog.repository;

import com.akash.blog.entity.Comment;
import com.akash.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long posId);

    @Query("SELECT c FROM Comment c WHERE "+
            "c.name LIKE CONCAT('%', :query, '%')"+
            "Or c.email LIKE CONCAT('%', :query, '%')"+
            "Or c.body LIKE CONCAT('%', :query, '%')"
    )
    List<Comment> searchComments(String query);

}
