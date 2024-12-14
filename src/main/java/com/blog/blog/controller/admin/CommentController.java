package com.blog.blog.controller.admin;

import com.blog.blog.model.Comment;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String index(Model model) {
        List<Comment> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        return "admin/comments/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("comment", new Comment());
        return "admin/comments/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Comment comment) {
        commentService.saveComment(comment);
        return "redirect:/admin/comments";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Comment comment = commentService.getCommentById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        model.addAttribute("comment", comment);
        return "admin/comments/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Comment comment) {
        comment.setId(id);
        commentService.saveComment(comment);
        return "redirect:/admin/comments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/admin/comments";
    }
}
