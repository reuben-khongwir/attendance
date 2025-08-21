//package com.spring.attendance.controller;
//
//import com.spring.attendance.controller.dto.StudentDto;
//import com.spring.attendance.service.StudentService;
//import com.spring.attendance.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/teacher/crud-students")
//public class StudentController {
//
//    private final StudentService studentService;
//    private final UserService userService; // to fetch User by email
//
//    public StudentController(StudentService studentService, UserService userService) {
//        this.studentService = studentService;
//        this.userService = userService;
//    }
//
//    // List students for logged-in teacher
//    @GetMapping
//    public String listStudents(Model model, Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        Long userId = userService.getUserByEmail(email).getId();
//
//        model.addAttribute("students", studentService.getAllStudentsByUser(userId));
//        return "list-students";
//    }
//
//    // Show add form
//    @GetMapping("/teacher/crud-students/new-student")
//    public String createStudentForm(Model model) {
//        model.addAttribute("student", new StudentDto());
//        return "create_student";
//    }
//
//    // Save student
//    @PostMapping
//    public String saveStudent(@ModelAttribute("student") StudentDto studentDto, Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        Long userId = userService.getUserByEmail(email).getId();
//
//        studentService.saveStudent(studentDto, userId);
//        return "redirect:/list-students";
//    }
//
//    // Edit
//    @GetMapping("/teacher/crud-students/edit/{id}")
//    public String editStudentForm(@PathVariable Long id, Model model) {
//        model.addAttribute("student", studentService.getStudentById(id));
//        return "edit_student";
//    }
//
//    // Update
//    @PostMapping("/teacher/crud-students/{id}")
//    public String updateStudent(@PathVariable Long id,
//                                @ModelAttribute("student") StudentDto studentDto,
//                                Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        Long userId = userService.getUserByEmail(email).getId();
//
//        studentDto.setId(id);
//        studentService.updateStudent(studentDto, userId);
//        return "redirect:/list-students";
//    }
//
//    // Delete
//    @GetMapping("/teacher/crud-students/delete/{id}")
//    public String deleteStudent(@PathVariable Long id) {
//        studentService.deleteStudentById(id);
//        return "redirect:/list-students";
//    }
//}
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

//@Controller
//@RequestMapping("/teacher/crud-students")
//public class StudentController {
//
//    private final StudentService studentService;
//    private final UserService userService; // to fetch User by email
//
//    public StudentController(StudentService studentService, UserService userService) {
//        this.studentService = studentService;
//        this.userService = userService;
//    }
//
//    // List students for logged-in teacher
////    @GetMapping
////    public String listStudents(Model model, Authentication authentication) {
////        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
////        Long userId = userService.getUserByEmail(email).getId();
////
////        model.addAttribute("students", studentService.getAllStudentsByUser(userId));
////        return "/teacher-dashboard/list-students"; // thymeleaf view
////    }
//    @GetMapping
//    public String listStudents(Model model, Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        User user = userService.getUserByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
//
//        model.addAttribute("students", studentService.getAllStudentsByUser(user.getId()));
//        return "/teacher-dashboard/list-students"; // thymeleaf view
//    }
//
//    // Show add form
//    @GetMapping("/new")
//    public String createStudentForm(Model model) {
//        model.addAttribute("student", new StudentDto());
//        return "/teacher-dashboard/create_student";
//    }
//
//    // Save student
////    @PostMapping
////    public String saveStudent(@ModelAttribute("student") StudentDto studentDto, Authentication authentication) {
////        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
////        Long userId = userService.getUserByEmail(email).getId();
////
////        studentService.saveStudent(studentDto, userId);
////        return "redirect:/teacher/crud-students";
////    }
//    @PostMapping
//    public String saveStudent(@ModelAttribute("student") StudentDto studentDto, Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        User user = userService.getUserByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
//
//        studentService.saveStudent(studentDto, user.getId());
//        return "redirect:/teacher/crud-students";
//    }
//
//    // Edit form
//    @GetMapping("/edit/{id}")
//    public String editStudentForm(@PathVariable Long id, Model model) {
//        model.addAttribute("student", studentService.getStudentById(id));
//        return "/teacher-dashboard/edit_student";
//    }
//
//    // Update student
////    @PostMapping("/{id}")
////    public String updateStudent(@PathVariable Long id,
////                                @ModelAttribute("student") StudentDto studentDto,
////                                Authentication authentication) {
////        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
////        Long userId = userService.getUserByEmail(email).getId();
////
////        studentDto.setId(id);
////        studentService.updateStudent(studentDto, userId);
////        return "redirect:/teacher/crud-students";
////    }
//    @PostMapping("/{id}")
//    public String updateStudent(@PathVariable Long id,
//                                @ModelAttribute("student") StudentDto studentDto,
//                                Authentication authentication) {
//        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
//        User user = userService.getUserByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
//
//        studentDto.setId(id);
//        studentService.updateStudent(studentDto, user.getId());
//        return "redirect:/teacher/crud-students";
//    }
//
//    // Delete student
//    @GetMapping("/delete/{id}")
//    public String deleteStudent(@PathVariable Long id) {
//        studentService.deleteStudentById(id);
//        return "redirect:/teacher/crud-students";
//    }
//}
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
