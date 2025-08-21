package com.spring.attendance.mapper;

import com.spring.attendance.dto.AttendanceDto;
import com.spring.attendance.dto.StudentDto;
import com.spring.attendance.model.Attendance;

public class AttendanceMapper {

    public static AttendanceDto toDto(Attendance attendance) {
        if (attendance == null) return null;

        StudentDto studentDto = StudentMapper.mapToStudentDto(attendance.getStudent());

        return new AttendanceDto(
                attendance.getId(),
                studentDto,
                attendance.getDate(),
                attendance.getStatus()
        );
    }
}
