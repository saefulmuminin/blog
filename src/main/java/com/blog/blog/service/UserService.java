package com.blog.blog.service;

import com.blog.blog.dto.UserDto;
import com.blog.blog.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
}