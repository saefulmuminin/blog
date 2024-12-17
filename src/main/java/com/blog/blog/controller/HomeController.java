package com.blog.blog.controller;

import com.blog.blog.model.Comment;
import com.blog.blog.model.News;
import com.blog.blog.model.User;
import com.blog.blog.service.CategoryService;
import com.blog.blog.service.CommentService;
import com.blog.blog.service.NewsService;
import com.blog.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("tagList", tagService.getAllTags());
        return "home"; // Mengarahkan ke template Thymeleaf "home.html"
    }

    @GetMapping("/news/category/{categoryId}")
    public String getNewsByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("newsList", newsService.getNewsByCategory(categoryId));
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("tagList", tagService.getAllTags());
        return "home"; // Kembali ke view yang sesuai
    }

    @GetMapping("/news/tag/{tagId}")
    public String getNewsByTag(@PathVariable("tagId") Long tagId, Model model) {
        model.addAttribute("newsList", newsService.getNewsByTag(tagId));
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("tagList", tagService.getAllTags());
        return "home";
    }

    @GetMapping("/news/{newsId}")
    public String newsDetails(@PathVariable Long newsId, Model model) {
        News news = newsService.findById(newsId);
        if (news != null) {
            model.addAttribute("news", news);

            // Mengambil daftar komentar untuk berita tersebut
            List<Comment> comments = commentService.getCommentsByNewsId(newsId);
            model.addAttribute("comments", comments);
        } else {
            // Jika berita tidak ditemukan, kembali ke halaman utama
            return "redirect:/";
        }

        return "newsDetails"; // Template untuk news details
    }

    @PostMapping("/news/{newsId}/comment")
    public String postComment(@PathVariable Long newsId, @RequestParam("content") String content,
            RedirectAttributes redirectAttributes) {
        if (content != null && !content.isEmpty()) {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof User) {
                    // Simpan komentar
                    try {
                        commentService.saveComment(newsId, content);
                        redirectAttributes.addFlashAttribute("message", "Komentar berhasil disimpan!");
                    } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("message", "Gagal menyimpan komentar: " + e.getMessage());
                    }
                } else {
                    throw new IllegalArgumentException("User tidak ditemukan dalam konteks keamanan");
                }
            } else {
                return "redirect:/login";
            }
        }
        return "redirect:/news/" + newsId;
    }

    @GetMapping("/news/{newsId}/comments")
    public String getCommentsByNews(@PathVariable Long newsId, Model model) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId);
        model.addAttribute("comments", comments);
        return "commentsList"; // Template terpisah untuk komentar
    }
}
