package com.blog.blog.dto;

import com.blog.blog.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Silakan masukkan nama yang valid.")
    private String name;

    @NotEmpty(message = "Silakan masukkan email yang valid.")
    @Email
    private String email;

    @NotEmpty(message = "Silakan masukkan kata sandi yang valid.")
    private String password;

    private Long roleId;

    // Getter for roleId to be used when updating the user
    public Long getRoleId() {
        return roleId;
    }

    // Konstruktor untuk membuat UserDto dari User
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = null; // Password tidak ditampilkan saat editing
        this.roleId = user.getRoles().isEmpty() ? null : user.getRoles().get(0).getId(); // Ambil ID dari role pertama
    }
}
