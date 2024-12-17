package com.blog.blog.controller.auth;

import com.blog.blog.dto.UserDto;
import com.blog.blog.model.User;
import com.blog.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Route untuk halaman login
    @RequestMapping("/login")
    public String loginForm(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return loginSuccess(authentication); // Jika sudah login, redirect ke dashboard sesuai role
        }
        return "auth/login"; // Tampilkan halaman login
    }

    // Route untuk halaman registrasi
    @GetMapping("/registration")
    public String registrationForm(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return loginSuccess(authentication); // Jika sudah login, redirect ke dashboard sesuai role
        }
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "auth/register"; // Tampilkan halaman registrasi
    }

    // Menangani form registrasi
    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
    
        if (existingUser != null) {
            result.rejectValue("email", "email.exists", "Pengguna sudah terdaftar!!!");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "auth/register"; // Jika ada error, tampilkan form registrasi lagi
        }
    
        userService.saveUser(userDto); // Simpan user baru
        return "redirect:/login"; // Redirect ke halaman login dengan parameter success
    }
    
    // Route untuk redirect sesuai role setelah login berhasil
    @RequestMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString(); // Mendapatkan role

        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard"; // Redirect ke dashboard admin
        } else if (role.contains("ROLE_USER")) {
            return "redirect:/user/dashboard"; // Redirect ke dashboard user
        } else {
            return "redirect:/"; // Redirect ke halaman default
        }
    }

    // Route untuk logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login"; // Redirect ke halaman login setelah logout
    }
}
