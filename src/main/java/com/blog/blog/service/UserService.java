package com.blog.blog.service;

import java.util.List;

import com.blog.blog.dto.UserDto;
import com.blog.blog.model.User;

public interface UserService {
    /**
     * Menyimpan pengguna baru berdasarkan data dari UserDto.
     * @param userDto DTO yang berisi data pengguna.
     */
    void saveUser(UserDto userDto);

    /**
     * Menemukan pengguna berdasarkan email.
     * @param email Email pengguna.
     * @return User yang sesuai dengan email.
     */
    User findUserByEmail(String email);

    /**
     * Menemukan pengguna berdasarkan username.
     * @param username Username pengguna.
     * @return User yang sesuai dengan username.
     */
    User findByEmail(String email);
    User findUserById(Long id);
    void updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    List<User> findAllUsers();

}
