package com.blog.blog.service;

import com.blog.blog.dto.UserDto;
import com.blog.blog.model.Role;
import com.blog.blog.model.User;
import com.blog.blog.repository.RoleRepository;
import com.blog.blog.repository.UserRepository;
import com.blog.blog.util.TbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
public void saveUser(UserDto userDto) {
    // Ensure a user role exists, or create it
    Role role = roleRepository.findByName(TbConstants.Roles.USER);
    
    if (role == null) {
        role = new Role(TbConstants.Roles.USER);
        role = roleRepository.save(role);
    }

    // Create a new User entity with encoded password
    User user = new User(userDto.getName(), userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()), Collections.singletonList(role));
    
    // Save the user to the database
    userRepository.save(user);
}

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
