package com.blog.blog.repository;

import com.blog.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find Category by name
    List<Category> findByName(String name);

    // Find Category by description
    List<Category> findByDescriptionContaining(String description);
}
