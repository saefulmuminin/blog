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
import java.util.List;
import java.util.Optional;
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

        // Check if password is provided
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
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

    @Override
    public User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new RuntimeException("Pengguna dengan email '" + email + "' tidak ditemukan."));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengguna dengan ID '" + id + "' tidak ditemukan."));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        User user = findUserById(id);

        // Update user details
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // If new password is provided, update the password
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Verify and update role
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role dengan ID " + userDto.getRoleId() + " tidak ditemukan."));

        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Pengguna dengan ID '" + id + "' tidak ditemukan.");
        }
        userRepository.deleteById(id);
    }
}
