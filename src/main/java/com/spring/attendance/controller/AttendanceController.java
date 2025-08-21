
package com.spring.attendance.controller;

import com.spring.attendance.dto.AttendanceDto;
import com.spring.attendance.model.Student;
import com.spring.attendance.model.User;
import com.spring.attendance.repository.StudentRepository;
import com.spring.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher/attendance")
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/mark")
    public String showMonthlyAttendance(@RequestParam(required = false) Integer year,
                                        @RequestParam(required = false) Integer month,
                                        Model model,
                                        @AuthenticationPrincipal User loggedUser) {
        if (loggedUser == null) return "redirect:/login";

        List<Student> students = studentRepository.findByUserId(loggedUser.getId());

        LocalDate today = LocalDate.now();
        int selYear = (year != null) ? year : today.getYear();
        int selMonth = (month != null) ? month : today.getMonthValue();

        // Generate all dates in selected month
        LocalDate firstDay = LocalDate.of(selYear, selMonth, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
        List<LocalDate> monthDates = firstDay.datesUntil(lastDay.plusDays(1)).toList();

    // Fetch all attendance for these students in this month
        Map<Long, Map<String, String>> attendanceMap = students.stream()
                .collect(Collectors.toMap(
                        Student::getId,
                        s -> monthDates.stream()
                                .collect(Collectors.toMap(
                                        date -> date.toString(), // key yyyy-MM-dd
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

        return "/teacher-dashboard/attendance_monthly";
    }

    @PostMapping("/save")
    public String saveMonthlyAttendance(@RequestParam Map<String,String> params,
                                        @AuthenticationPrincipal User loggedUser,
                                        @RequestParam int year,
                                        @RequestParam int month) {

        List<Student> students = studentRepository.findByUserId(loggedUser.getId());

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        List<LocalDate> monthDates = firstDay.datesUntil(lastDay.plusDays(1)).toList();
        for (Student student : students) {
            for (LocalDate date : monthDates) {
                String key = "attendance_" + student.getId() + "_" + date.toString();

                if (date.isAfter(LocalDate.now())) {
                    // skip future dates, don't save anything
                    continue;
                }

                String status = params.containsKey(key) ? "PRESENT" : "ABSENT";
                attendanceService.markAttendance(student, date, status);
            }
        }

        return "redirect:/teacher/attendance/mark?year=" + year + "&month=" + month;
    }


}
