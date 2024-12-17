package com.blog.blog.service;

import com.blog.blog.model.Comment;
import com.blog.blog.model.News;
import com.blog.blog.model.User;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsRepository newsRepository;

    // Simpan komentar
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }


    public List<Comment> getCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsId(newsId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Comment with ID " + id + " does not exist.");
        }
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void saveComment(Long newsId, String content) {
        Optional<News> newsOptional = newsRepository.findById(newsId);
        if (!newsOptional.isPresent()) {
            throw new RuntimeException("News with ID " + newsId + " not found.");
        }
    
        News news = newsOptional.get();
    
        // Check for authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            Comment comment = new Comment();
            comment.setNews(news);
            comment.setContent(content);
            comment.setUser(user);
            comment.setCreatedAt(LocalDateTime.now());
            
            // Save comment
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("User tidak ditemukan dalam konteks keamanan");
        }
    }
    

}
