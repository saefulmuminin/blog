package com.blog.blog.util;

import com.blog.blog.model.Role;
import com.blog.blog.service.UserService;
import com.blog.blog.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataSeeder implements CommandLineRunner {


    private final RoleService roleService;

    public DataSeeder(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Fetch the "ADMIN" role by name
        Role adminRole = roleService.findByName("ADMIN");

        // If the "ADMIN" role doesn't exist, create and save it
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleService.save(adminRole);
        }
    }
}
