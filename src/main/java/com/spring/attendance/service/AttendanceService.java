package com.spring.attendance.service;

import com.spring.attendance.dto.AttendanceDto;
import com.spring.attendance.mapper.AttendanceMapper;
import com.spring.attendance.model.Attendance;
import com.spring.attendance.model.Student;
import com.spring.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<AttendanceDto> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date).stream()
                .map(AttendanceMapper::toDto)
                .collect(Collectors.toList());
    }
//    public List<AttendanceDto> getAttendanceForStudentsInMonth(List<Long> studentIds, LocalDate start, LocalDate end) {
//        return attendanceRepository.findByStudentIdInAndDateBetween(studentIds, start, end)
//                .stream()
//                .map(AttendanceMapper::toDto)
//                .collect(Collectors.toList());
//    }

    public AttendanceDto markAttendance(Student student, LocalDate date, String status) {
        Optional<Attendance> existing = attendanceRepository.findByStudentIdAndDate(student.getId(), date);
        Attendance attendance;
        if (existing.isPresent()) {
            attendance = existing.get();
            attendance.setStatus(status);
        } else {
            attendance = new Attendance(student, date, status);
        }
        return AttendanceMapper.toDto(attendanceRepository.save(attendance));
    }
}


