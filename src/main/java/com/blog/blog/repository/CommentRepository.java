package com.blog.blog.repository;

import com.blog.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find Comments by News
    List<Comment> findByNewsId(Long newsId);

    // Find Comments by User
    List<Comment> findByUserId(Long userId);

    // Find Comments containing a specific content
    List<Comment> findByContentContaining(String content);

      // To fetch comments by news id
    void deleteByNewsId(Long newsId);
}
