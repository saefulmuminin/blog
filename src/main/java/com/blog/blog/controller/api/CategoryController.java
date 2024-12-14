package com.blog.blog.controller.api;

import com.blog.blog.model.Category;
import com.blog.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create or Update category
    @PostMapping
    public Category createOrUpdateCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    // Get category by ID
    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    // Get all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Get categories by name
    @GetMapping("/search")
    public List<Category> searchCategoriesByName(@RequestParam String name) {
        return categoryService.getCategoriesByName(name);
    }

    // Search categories by description
    @GetMapping("/search/description")
    public List<Category> searchCategoriesByDescription(@RequestParam String description) {
        return categoryService.searchCategoriesByDescription(description);
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
