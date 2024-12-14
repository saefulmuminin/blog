package com.blog.blog.controller.api;

import com.blog.blog.model.Tag;
import com.blog.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Create or Update tag
    @PostMapping
    public Tag createOrUpdateTag(@RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }

    // Get tag by ID
    @GetMapping("/{id}")
    public Optional<Tag> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    // Get all tags
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    // Search tags by name
    @GetMapping("/search")
    public List<Tag> searchTagsByName(@RequestParam String name) {
        return tagService.searchTagsByName(name);
    }

    // Delete tag by ID
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
