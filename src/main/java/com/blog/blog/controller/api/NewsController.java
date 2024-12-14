package com.blog.blog.controller.api;

import com.blog.blog.model.News;
import com.blog.blog.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Get all news articles along with comments
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    // Get a specific news article by ID with comments
    @GetMapping("/{id}")
    public Optional<News> getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    // Create a new news article
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    // Update a news article
    @PutMapping("/{id}")
    public News updateNews(@PathVariable Long id, @RequestBody News news) {
        return newsService.updateNews(id, news);
    }

    // Delete a news article
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
