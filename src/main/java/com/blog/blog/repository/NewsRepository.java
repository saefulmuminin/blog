package com.blog.blog.repository;

import com.blog.blog.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByCategoryId(Long categoryId);

    List<News> findByAuthorId(Long authorId);

    List<News> findByTitleContaining(String title);
}
