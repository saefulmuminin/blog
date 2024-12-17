package com.blog.blog.service;

import com.blog.blog.model.Comment;
import com.blog.blog.model.News;
import com.blog.blog.repository.NewsRepository;
import com.blog.blog.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository; // Tambahan untuk repository komentar

    @Value("${upload.dir}")
    private String uploadDir; // Mengambil nilai 'upload.dir' dari application properties

    // Constructor Injection
    public NewsService(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public List<News> getNewsByCategory(Long categoryId) {
        return newsRepository.findByCategoryId(categoryId);
    }

    public Optional<News> getNewsByTag(Long tagId) {
        return newsRepository.findById(tagId);
    }

    public News saveNews(News news, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            news.setImage(fileName);
        }

        // Pastikan category_id tidak null
        if (news.getCategory() == null) {
            throw new IllegalArgumentException("Category ID tidak boleh kosong");
        }

        return newsRepository.save(news);
    }

    public News updateNews(News news, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            news.setImage(fileName);
        }

        // Pastikan category_id tidak null
        if (news.getCategory() == null) {
            throw new IllegalArgumentException("Category ID tidak boleh kosong");
        }

        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent()); // Membuat direktori jika belum ada
        Files.copy(file.getInputStream(), filePath); // Menyimpan file ke lokasi
        return fileName;
    }

    public News findById(Long newsId) {
        return newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News with id " + newsId + " not found"));
    }

    public List<Comment> getCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsId(newsId);
    }

    public Comment saveComment(Long newsId, Comment comment) {
        Optional<News> newsOpt = newsRepository.findById(newsId);
        if (newsOpt.isPresent()) {
            comment.setNews(newsOpt.get());
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("News with id " + newsId + " not found");
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
