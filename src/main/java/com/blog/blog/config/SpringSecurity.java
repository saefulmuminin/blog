package com.blog.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.blog.blog.util.TbConstants;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/assets/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login", "/registration").anonymous() // Hanya bisa diakses oleh pengguna yang belum login
                .requestMatchers("/admin/**").hasAuthority(TbConstants.Roles.ADMIN) // Hanya bisa diakses oleh pengguna dengan role ADMIN
                .requestMatchers("/user/**").hasAuthority(TbConstants.Roles.USER) // Hanya bisa diakses oleh pengguna dengan role USER
                .anyRequest().permitAll() // Semua request lainnya diperbolehkan
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(new CustomAuthenticationSuccessHandler()) // Custom Success Handler
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Menggunakan BCrypt untuk password hashing
    }
}
