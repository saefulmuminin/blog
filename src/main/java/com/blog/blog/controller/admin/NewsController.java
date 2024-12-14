package com.blog.blog.controller.admin;

import com.blog.blog.model.News;
import com.blog.blog.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public String index(Model model) {
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        return "admin/news/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("news", new News());
        return "admin/news/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute News news) {
        newsService.saveNews(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        News news = newsService.getNewsById(id).orElseThrow(() -> new RuntimeException("News not found"));
        model.addAttribute("news", news);
        return "admin/news/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute News news) {
        news.setId(id);
        newsService.saveNews(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "redirect:/admin/news";
    }
}
