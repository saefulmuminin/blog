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

    // Get all tags
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // Get tag by ID
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // Save or update a tag
    public Tag saveOrUpdateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Delete tag by ID
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    // Find tags by name
    public List<Tag> findTagsByName(String name) {
        return tagRepository.findByNameContaining(name);
    }

    // Find Tags by an array of IDs
    public List<Tag> findByIds(Long tagId) {
        if (tagId == null || tagId == 0) {
            return List.of();
        }
        return tagRepository.findAllById(List.of(tagId));
    }
}
