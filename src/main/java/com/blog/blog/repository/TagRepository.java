package com.blog.blog.repository;

import com.blog.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Find Tag by name
    List<Tag> findByName(String name);

    // Find Tag by partial name (like search)
    List<Tag> findByNameContaining(String name);
}
