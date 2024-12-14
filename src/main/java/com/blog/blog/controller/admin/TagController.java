package com.blog.blog.controller.admin;

import com.blog.blog.model.Tag;
import com.blog.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String index(Model model) {
        List<Tag> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);
        return "admin/tags/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Tag tag) {
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Tag tag = tagService.getTagById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        model.addAttribute("tag", tag);
        return "admin/tags/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Tag tag) {
        tag.setId(id);
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
