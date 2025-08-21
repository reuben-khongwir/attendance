package com.spring.attendance.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleBasedLogout implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String redirectUrl = "/login?logout"; // fallback

        if (authentication != null && authentication.getAuthorities() != null) {
            var authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                redirectUrl = "/login?role=ADMIN&logout";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("TEACHER"))) {
                redirectUrl = "/login?role=TEACHER&logout";
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
