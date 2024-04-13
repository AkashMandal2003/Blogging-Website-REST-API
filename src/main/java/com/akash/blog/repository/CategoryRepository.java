package com.akash.blog.repository;

import com.akash.blog.entity.Category;
import com.akash.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT c FROM Category c WHERE "+
            "c.name LIKE CONCAT('%', :query, '%')"+
            "Or c.description LIKE CONCAT('%', :query, '%')"
    )
    List<Category> searchCategory(String query);
}
