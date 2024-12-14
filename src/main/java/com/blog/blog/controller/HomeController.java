package com.blog.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    // Route untuk halaman utama (akses bebas tanpa login)
    @GetMapping("/")
    public String home() {
        return "home";  // Halaman ini bisa diakses oleh siapa saja tanpa login
    }

    // Route untuk halaman pengguna (USER role)
    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal User user) {
        if (user != null) {
            // Menampilkan nama pengguna
            return "user";  // Halaman yang hanya bisa diakses oleh role USER
        }
        return "redirect:/login";  // Jika tidak login, arahkan ke halaman login
    }

    // Route untuk halaman admin (ADMIN role)
    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal User user) {
        if (user != null && user.getAuthorities().toString().contains("ADMIN")) {
            // Jika pengguna memiliki role ADMIN
            return "admin";  // Halaman yang hanya bisa diakses oleh role ADMIN
        }
        return "redirect:/login";  // Jika tidak login atau bukan ADMIN, arahkan ke halaman login
    }
}
