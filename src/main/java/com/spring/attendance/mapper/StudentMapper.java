package com.spring.attendance.mapper;

import com.spring.attendance.dto.StudentDto;
import com.spring.attendance.model.Student;

public class StudentMapper {

    public static StudentDto mapToStudentDto(Student student) {
        // Get teacher full name if present
        String teacherName = "";
        if (student.getUser() != null) {
            teacherName = student.getUser().getFirstName() + " " + student.getUser().getLastName();
        }

        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                teacherName
        );
    }

    public static Student mapToStudent(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        return student;
    }
}
