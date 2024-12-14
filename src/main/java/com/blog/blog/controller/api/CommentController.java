package com.blog.blog.controller.api;

import com.blog.blog.model.Comment;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create or Update comment
    @PostMapping
    public Comment createOrUpdateComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    // Get comment by ID
    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    // Get comments by news ID
    @GetMapping("/news/{newsId}")
    public List<Comment> getCommentsByNewsId(@PathVariable Long newsId) {
        return commentService.getCommentsByNewsId(newsId);
    }

    // Get comments by user ID
    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUserId(@PathVariable Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    // Search comments by content
    @GetMapping("/search")
    public List<Comment> searchCommentsByContent(@RequestParam String content) {
        return commentService.searchCommentsByContent(content);
    }

    // Delete comment by ID
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
