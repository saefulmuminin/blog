package com.blog.blog.controller.user;

import com.blog.blog.model.User;  // Make sure this imports your custom User model
import com.blog.blog.service.UserService;  // Import UserService
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class UserDashboardController {

    @Autowired
    private UserService userService; // Injecting UserService to fetch user details

    // Halaman Dashboard untuk User
    @GetMapping("/user/dashboard")
    public String dashboard(Model model) {
        // Get the currently authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();  // Get the username (email) of the authenticated user

        // Fetch the full user details using the email
        User user = userService.findUserByEmail(email);

        // Add the user data to the model
        model.addAttribute("name", user.getName());  // Assuming User has getName()
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRoles().get(0).getName());  // Assuming the user has only one role

        // Return the user dashboard view
        return "user/dashboard";  // This should map to your user dashboard template (Thymeleaf)
    }
}
