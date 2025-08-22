
package com.spring.attendance.controller;

import com.spring.attendance.dto.StudentDto;
import com.spring.attendance.model.User;
import com.spring.attendance.service.StudentService;
import com.spring.attendance.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/teacher/students")
public class TeacherStudentController {

    private final StudentService studentService;
    private final UserService userService;

    public TeacherStudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public String listStudents(Model model, Authentication authentication) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Long teacherId = userService.getUserByEmail(email).orElseThrow().getId();

        model.addAttribute("students", studentService.getAllStudentsByTeacher(teacherId));
        return "/teacher-dashboard/list-students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "/teacher-dashboard/edit_student";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") StudentDto dto,
                                Authentication authentication) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Long teacherId = userService.getUserByEmail(email).orElseThrow().getId();

        dto.setId(id);
        studentService.updateStudentByTeacher(dto, teacherId);
        return "redirect:/teacher/students";
    }
}
