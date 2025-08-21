package com.spring.attendance.controller;

import com.spring.attendance.dto.StudentDto;
import com.spring.attendance.dto.TeacherDto;
import com.spring.attendance.dto.UserApprovalDto;
import com.spring.attendance.model.User;
import com.spring.attendance.repository.StudentRepository;
import com.spring.attendance.repository.UserRepository;
import com.spring.attendance.service.AdminService;
import com.spring.attendance.service.StudentService;
import com.spring.attendance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AdminService adminService;
    private final StudentService studentService;
    private final UserService userService;

    public AdminController(UserRepository userRepository,
                           StudentRepository studentRepository,
                           AdminService adminService,
                           StudentService studentService,
                           UserService userService) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.adminService = adminService;
        this.studentService = studentService;
        this.userService = userService;
    }

    // ------------------ Teacher Management ------------------

    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        List<User> teachers = userRepository.findByRoles_Name("TEACHER");
        model.addAttribute("teachers", teachers);
        return "/admin-dashboard/admin_teachers";
    }

    @GetMapping("/pending-teachers")
    public String listPendingTeachers(Model model) {
        List<User> pending = adminService.getPendingTeachers();
        model.addAttribute("pendingTeachers", pending);
        return "/admin-dashboard/admin_pending_teachers";
    }

    @PostMapping("/approve-teacher")
    public String approveOrRejectTeacher(@ModelAttribute UserApprovalDto dto) {
        adminService.updateTeacherStatus(dto);
        return "redirect:/admin/pending-teachers";
    }

    @GetMapping("/teacher/{id}")
    public String viewTeacher(@PathVariable Long id, Model model) {
        User teacher = adminService.getTeacher(id);
        model.addAttribute("teacher", teacher);
        return "/admin-dashboard/admin_view_teacher";
    }

    @GetMapping("/teacher/edit/{id}")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        User teacher = adminService.getTeacher(id);
        model.addAttribute("teacherDto", teacher);
        return "/admin-dashboard/admin_edit_teacher";
    }

    @PostMapping("/teacher/edit")
    public String editTeacherSubmit(@ModelAttribute TeacherDto teacherDto) {
        adminService.updateTeacher(teacherDto);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        adminService.deleteTeacher(id);
        return "redirect:/admin/teachers";
    }

    // ------------------ Student Management ------------------

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "/admin-dashboard/admin_students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new StudentDto());
        model.addAttribute("teachers", userService.getAllTeachers()); // dropdown to assign
        return "/admin-dashboard/admin_create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") StudentDto dto,
                              @RequestParam("teacherId") Long teacherId) {
        studentService.saveStudentByAdmin(dto, teacherId);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("teachers", userService.getAllTeachers());
        return "/admin-dashboard/admin_edit_student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") StudentDto dto,
                                @RequestParam("teacherId") Long teacherId) {
        dto.setId(id);
        studentService.updateStudentByAdmin(dto, teacherId);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentByAdmin(id);
        return "redirect:/admin/students";
    }
}
