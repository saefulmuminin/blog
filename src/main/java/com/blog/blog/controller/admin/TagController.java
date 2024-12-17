package com.blog.blog.controller.admin;

import com.blog.blog.model.Tag;
import com.blog.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Menampilkan semua tag
    @GetMapping
    public String listTags(Model model, @RequestParam(value = "search", required = false) String search) {
        List<Tag> tags = (search != null && !search.isEmpty()) 
                ? tagService.findTagsByName(search)
                : tagService.getAllTags();
        model.addAttribute("tags", tags);
        model.addAttribute("search", search);
        return "admin/tags/index";
    }

    // Menampilkan form untuk membuat tag baru
    @GetMapping("/create")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags/create";
    }

    // Menyimpan tag baru
    @PostMapping("/save")
    public String saveTag(@ModelAttribute Tag tag) {
        tagService.saveOrUpdateTag(tag);
        return "redirect:/admin/tags";
    }

    // Menampilkan form untuk mengedit tag
    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable Long id, Model model) {
        Optional<Tag> tagOptional = tagService.getTagById(id);
        if (tagOptional.isPresent()) {
            model.addAttribute("tag", tagOptional.get());
            return "admin/tags/edit";
        } else {
            return "redirect:/admin/tags";
        }
    }

    // Memperbarui tag
    @PostMapping("/update/{id}")
    public String updateTag(@PathVariable Long id, @ModelAttribute Tag tag) {
        tag.setId(id); // Set ID agar tidak membuat entitas baru
        tagService.saveOrUpdateTag(tag);
        return "redirect:/admin/tags";
    }

    // Menghapus tag
    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
