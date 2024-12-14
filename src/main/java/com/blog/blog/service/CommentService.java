package com.blog.blog.service;

import com.blog.blog.model.Comment;
import com.blog.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Create or Update comment
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Get comment by ID
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    // Get comments by news ID
    public List<Comment> getCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsId(newsId);
    }

    // Get comments by user ID
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    // Search comments by content
    public List<Comment> searchCommentsByContent(String content) {
        return commentRepository.findByContentContaining(content);
    }

    // Delete comment by ID
    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Comment with ID " + id + " does not exist.");
        }
    }

    // Get all comments
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
