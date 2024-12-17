package com.blog.blog.controller.admin;

import com.blog.blog.model.Comment;
import com.blog.blog.model.News;
import com.blog.blog.service.CommentService;
import com.blog.blog.service.NewsService;
import com.blog.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {

    private final CommentService commentService;
    private final NewsService newsService;
    private final UserService userService;

    public CommentController(CommentService commentService, NewsService newsService, UserService userService) {
        this.commentService = commentService;
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public String listComments(Model model) {
        model.addAttribute("commentList", commentService.getAllComments());
        return "admin/comments/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("newsList", newsService.getAllNews());
        return "admin/comments/create";
    }

    @PostMapping("/save")
    public String saveComment(@ModelAttribute Comment comment, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            Optional<News> newsOptional = newsService.getNewsById(comment.getNews().getId());
            if (newsOptional.isPresent()) {
                comment.setNews(newsOptional.get());
                comment.setUser(userService.findByEmail(email));
                comment.setCreatedAt(LocalDateTime.now());

                commentService.saveComment(comment);
                redirectAttributes.addFlashAttribute("message", "Komentar berhasil disimpan!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Berita tidak ditemukan!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Gagal menyimpan komentar: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()) {
            model.addAttribute("comment", commentOptional.get());
            model.addAttribute("newsList", newsService.getAllNews());
            return "admin/comments/edit";
        } else {
            redirectAttributes.addFlashAttribute("message", "Komentar tidak ditemukan!");
            return "redirect:/admin/comments";
        }
    }

    @PostMapping("/update")
    public String updateComment(@ModelAttribute("comment") Comment updatedComment, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            Optional<News> newsOptional = newsService.getNewsById(updatedComment.getNews().getId());
            if (newsOptional.isPresent()) {
                updatedComment.setNews(newsOptional.get());
                updatedComment.setUser(userService.findByEmail(email));

                commentService.saveComment(updatedComment);
                redirectAttributes.addFlashAttribute("message", "Komentar berhasil diperbarui!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Berita tidak ditemukan!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Gagal memperbarui komentar: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Comment> commentOptional = commentService.getCommentById(id);
            if (commentOptional.isPresent()) {
                commentService.deleteComment(id);
                redirectAttributes.addFlashAttribute("message", "Komentar berhasil dihapus!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Komentar tidak ditemukan!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Gagal menghapus komentar: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }
}
