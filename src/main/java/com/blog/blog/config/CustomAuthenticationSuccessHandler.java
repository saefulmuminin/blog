package com.blog.blog.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.blog.blog.util.TbConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .findFirst()
            .orElse("");

        if (role.contains(TbConstants.Roles.ADMIN)) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.contains(TbConstants.Roles.USER)) {
            response.sendRedirect("/user/dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}
