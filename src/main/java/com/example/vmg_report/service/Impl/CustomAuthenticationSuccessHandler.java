package com.example.vmg_report.service.Impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        if (authentication != null && authentication.isAuthenticated()) {

            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
                response.sendRedirect("/Admin/home");
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("USER"))) {
                response.sendRedirect("/User/home");
            } else {

                response.sendRedirect("/");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}
