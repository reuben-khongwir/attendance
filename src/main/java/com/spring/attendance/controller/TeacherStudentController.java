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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/teacher/students")
public class TeacherStudentController {

    private final StudentService studentService;
    private final UserService userService;

    public TeacherStudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

//    @GetMapping
//    public String listStudents(Model model, Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        Long teacherId = userService.getUserByEmail(email).orElseThrow().getId();
//
//        model.addAttribute("students", studentService.getAllStudentsByTeacher(teacherId));
//        return "/teacher-dashboard/list-students";
//    }
    @GetMapping
    public String listStudents() {
        return "redirect:/teacher/students/page/1";
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



    @GetMapping("/page/{pageNo}")
    public String findPaginatedStudents(@PathVariable(value = "pageNo") int pageNo,
                                        Model model,
                                        Authentication authentication) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Long teacherId = userService.getUserByEmail(email).orElseThrow().getId();

        int pageSize = 3; // adjust per your preference
        Page<StudentDto> page = studentService.findPaginatedStudentsByTeacher(teacherId, pageNo, pageSize);
        List<StudentDto> students = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("students", students);

        return "/teacher-dashboard/list-students";
    }

}
