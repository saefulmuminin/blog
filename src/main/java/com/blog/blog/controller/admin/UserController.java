package com.blog.blog.controller.admin;

import com.blog.blog.dto.UserDto;
import com.blog.blog.model.User;
import com.blog.blog.service.RoleService;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // Display list of users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users/index"; // View for listing users
    }

    // Show form for creating a new user
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/users/create";
    }

    // Save a new user
    @PostMapping
    public String createUser(@ModelAttribute UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/admin/users";
    }

    // Show form for editing a user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("userDto", new UserDto(user)); // Populate form with user data
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/users/edit";
    }

    // Update an existing user
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        userService.updateUser(id, userDto);
        return "redirect:/admin/users";
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
