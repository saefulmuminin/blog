package com.blog.blog.controller.admin;

import com.blog.blog.model.User;  // Make sure this imports your custom User model
import com.blog.blog.service.UserService;  // Import UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService; // Injecting UserService to fetch user details

    // Halaman Dashboard untuk Admin
    @GetMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Get the currently authenticated user (email is stored as username)
        String email = userDetails.getUsername();  // Get the username (email) of the authenticated user

        // Fetch the full user details using the email
        User fetchedUser = userService.findUserByEmail(email);

        // Add the user data to the model
        model.addAttribute("name", fetchedUser.getName());  // Assuming User has getName()
        model.addAttribute("email", fetchedUser.getEmail());
        model.addAttribute("role", fetchedUser.getRoles().get(0).getName());  // Assuming the user has only one role

        return "admin/dashboard";  // Return the dashboard view
    }
}
