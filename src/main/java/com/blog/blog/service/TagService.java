package com.blog.blog.service;

import com.blog.blog.model.Tag;
import com.blog.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    // Create or Update tag
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Get tag by ID
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // Get all tags
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // Get tag by name
    public List<Tag> getTagsByName(String name) {
        return tagRepository.findByName(name);
    }

    // Search tags by name
    public List<Tag> searchTagsByName(String name) {
        return tagRepository.findByNameContaining(name);
    }

    // Delete tag by ID
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
