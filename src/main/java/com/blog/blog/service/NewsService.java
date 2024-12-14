package com.blog.blog.service;

import com.blog.blog.model.News;
import com.blog.blog.model.Comment;
import com.blog.blog.repository.NewsRepository;
import com.blog.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CommentRepository commentRepository;  // Add the CommentRepository to access comments

    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    public Optional<News> getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            // Fetch and set comments for this news
            List<Comment> comments = commentRepository.findByNewsId(id);
            news.get().setComments(comments);
        }
        return news;
    }

    public List<News> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        // Optionally, fetch and set comments for each news
        for (News news : newsList) {
            List<Comment> comments = commentRepository.findByNewsId(news.getId());
            news.setComments(comments);
        }
        return newsList;
    }

    public List<News> getNewsByCategory(Long categoryId) {
        return newsRepository.findByCategoryId(categoryId);
    }

    public List<News> getNewsByAuthor(Long authorId) {
        return newsRepository.findByAuthorId(authorId);
    }

    public List<News> searchNewsByTitle(String title) {
        return newsRepository.findByTitleContaining(title);
    }

    public void deleteNews(Long id) {
        // First, delete comments associated with this news
        commentRepository.deleteByNewsId(id);
        newsRepository.deleteById(id);
    }

    public News updateNews(Long id, News news) {
        return newsRepository.findById(id)
                .map(existingNews -> {
                    existingNews.setTitle(news.getTitle());
                    existingNews.setContent(news.getContent());
                    existingNews.setCategory(news.getCategory());
                    existingNews.setTags(news.getTags());
                    existingNews.setAuthor(news.getAuthor());
                    return newsRepository.save(existingNews);
                })
                .orElseThrow(() -> new RuntimeException("News not found"));
    }
}
