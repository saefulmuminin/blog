package com.blog.blog.dto;

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
}
