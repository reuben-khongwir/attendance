package com.spring.attendance.dto;

import com.spring.attendance.model.AccountStatus;
import lombok.Data;

@Data
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private AccountStatus status;
    private Long teacherId; // admin assigns a teacher

}
