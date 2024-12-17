package com.blog.blog.controller.admin;

import com.blog.blog.model.News;
import com.blog.blog.model.Tag;
import com.blog.blog.model.User;
import com.blog.blog.service.CategoryService;
import com.blog.blog.service.NewsService;
import com.blog.blog.service.TagService;
import com.blog.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Controller
@RequestMapping("admin/news")
public class NewsController {

    private final NewsService newsService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;

    public NewsController(NewsService newsService, CategoryService categoryService, TagService tagService, UserService userService) {
        this.newsService = newsService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping
    public String listNews(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "admin/news/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/news/create";
    }

    @PostMapping("/save")
    public String saveNews(@ModelAttribute("news") News news, @RequestParam("imageFile") MultipartFile imageFile, @RequestParam("tags") Long[] tagIds, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User author = userService.findByEmail(email);
            news.setAuthor(author);

            if (news.getCategory() == null) {
                redirectAttributes.addFlashAttribute("message", "Pilih kategori terlebih dahulu.");
                return "redirect:/admin/news/create";
            }

            if (tagIds != null && tagIds.length > 0) {
                Set<Tag> tags = new HashSet<>();
                for (Long tagId : tagIds) {
                    Optional<Tag> tagOptional = tagService.getTagById(tagId);
                    tagOptional.ifPresent(tags::add);
                }
                news.setTags(tags);
            }

            if (imageFile.isEmpty() && news.getImage() == null) {
                redirectAttributes.addFlashAttribute("message", "Pilih gambar terlebih dahulu.");
                return "redirect:/admin/news/create";
            }

            newsService.saveNews(news, imageFile);
            redirectAttributes.addFlashAttribute("message", "Berita berhasil disimpan!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Gagal menyimpan berita: " + e.getMessage());
        }

        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<News> newsOptional = newsService.getNewsById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            model.addAttribute("news", news);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("tags", tagService.getAllTags());
            return "admin/news/edit";
        } else {
            return "redirect:/admin/news";
        }
    }

    @PostMapping("/update")
    public String updateNews(@ModelAttribute("news") News news, @RequestParam("imageFile") MultipartFile imageFile, @RequestParam("tags") Long[] tagIds, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User author = userService.findByEmail(email);
            news.setAuthor(author);

            if (news.getCategory() == null) {
                redirectAttributes.addFlashAttribute("message", "Pilih kategori terlebih dahulu.");
                return "redirect:/admin/news/edit/" + news.getId();
            }

            if (tagIds != null && tagIds.length > 0) {
                Set<Tag> tags = new HashSet<>();
                for (Long tagId : tagIds) {
                    Optional<Tag> tagOptional = tagService.getTagById(tagId);
                    tagOptional.ifPresent(tags::add);
                }
                news.setTags(tags);
            }

            // Menjaga gambar lama jika tidak ada gambar baru yang di-upload
            if (imageFile.isEmpty()) {
                News existingNews = newsService.getNewsById(news.getId()).get();
                news.setImage(existingNews.getImage());
            }

            newsService.updateNews(news, imageFile);
            redirectAttributes.addFlashAttribute("message", "Berita berhasil diperbarui!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Gagal memperbarui berita: " + e.getMessage());
        }

        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsService.deleteNews(id);
        redirectAttributes.addFlashAttribute("message", "Berita berhasil dihapus!");
        return "redirect:/admin/news";
    }
}
