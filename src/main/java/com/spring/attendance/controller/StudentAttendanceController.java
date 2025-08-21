package com.spring.attendance.controller;

import com.spring.attendance.dto.AttendanceDto;
import com.spring.attendance.model.Attendance;
import com.spring.attendance.model.Student;
import com.spring.attendance.model.User;
import com.spring.attendance.repository.AttendanceRepository;
import com.spring.attendance.repository.StudentRepository;
import com.spring.attendance.repository.UserRepository;
import com.spring.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student/attendance")
public class StudentAttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
//
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserRepository userRepository; // your Teacher/User repository

    @GetMapping("/view")
    public String viewAttendance(@RequestParam(required = false) Long teacherId,
                                 @RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) Integer month,
                                 Model model) {

        // Load all teachers for dropdown
        List<User> teachers = userRepository.findByRoles_Name("TEACHER");
        model.addAttribute("teachers", teachers);

        if (teacherId != null) {
            List<Student> students = studentRepository.findByUserId(teacherId);

            LocalDate today = LocalDate.now();
            int selYear = (year != null) ? year : today.getYear();
            int selMonth = (month != null) ? month : today.getMonthValue();

            LocalDate firstDay = LocalDate.of(selYear, selMonth, 1);
            LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
            List<LocalDate> monthDates = firstDay.datesUntil(lastDay.plusDays(1)).toList();

            Map<Long, Map<String, String>> attendanceMap = students.stream()
                    .collect(Collectors.toMap(
                            Student::getId,
                            s -> monthDates.stream()
                                    .collect(Collectors.toMap(
                                            date -> date.toString(),
                                            date -> attendanceService.getAttendanceByDate(date).stream()
                                                    .filter(a -> a.getStudent().getId().equals(s.getId()))
                                                    .map(AttendanceDto::getStatus)
                                                    .findFirst()
                                                    .orElse(date.isBefore(LocalDate.now()) || date.equals(LocalDate.now())
                                                            ? "ABSENT"
                                                            : "")
                                    ))
                    ));


            model.addAttribute("students", students);
            model.addAttribute("monthDates", monthDates);
            model.addAttribute("attendanceMap", attendanceMap);
            model.addAttribute("selectedYear", selYear);
            model.addAttribute("selectedMonth", selMonth);
            model.addAttribute("selectedTeacherId", teacherId);
        }


        return "student";
    }
}

