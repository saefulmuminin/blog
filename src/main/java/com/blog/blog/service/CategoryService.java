package com.blog.blog.service;

import com.blog.blog.model.Category;
import com.blog.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create or Update category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by name
    public List<Category> getCategoriesByName(String name) {
        return categoryRepository.findByName(name);
    }

    // Search categories by description
    public List<Category> searchCategoriesByDescription(String description) {
        return categoryRepository.findByDescriptionContaining(description);
    }

    // Delete category by ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
