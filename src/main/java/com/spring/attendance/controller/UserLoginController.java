
package com.spring.attendance.controller;

import com.spring.attendance.model.User;
import com.spring.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserLoginController {
    @Autowired
    private UserRepository userRepository;
    // Login page for all roles
    @GetMapping("/login")
    public String loginPage(Authentication authentication,
                            @RequestParam(value = "role", required = false) String role,
                            Model model) {

        if (authentication != null && authentication.isAuthenticated()) {
            var authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("TEACHER"))) {
                return "redirect:/teacher/dashboard";
            }
//            else {
//                return "redirect:/";
//            }
        }
        model.addAttribute("role", role);
        return "/authentication/login";
    }

    // Landing page
    @GetMapping("/")
    public String landingPage(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            // User already logged in, go to dashboard based on role
            var authorities = auth.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("TEACHER"))) {
                return "redirect:/teacher/dashboard";
            }
            else {
                return "redirect:/";
            }
        }
        return "landing"; // unauthenticated
    }

    // Admin dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "/admin-dashboard/admin";
    }

    // Teacher dashboard
    @GetMapping("/teacher/dashboard")
    public String teacherDashboard() {
        return "/teacher-dashboard/index";
    }

    // Student dashboard
    @GetMapping("/student/attendance")
    public String viewStudentAttendance(Model model) {
        // Fetch all users who are teachers (filter by role)
        List<User> teachers = userRepository.findAll(); // you may want to filter by role "TEACHER"

        model.addAttribute("teachers", teachers);
        return "student"; // Thymeleaf template name
    }

}
