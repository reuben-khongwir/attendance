package com.spring.attendance.dto;


import java.time.LocalDate;

public class AttendanceDto {
    private Long id;
    private StudentDto student; // use full StudentDto
    private LocalDate date;
    private String status;

    public AttendanceDto() {}

    public AttendanceDto(Long id, StudentDto student, LocalDate date, String status) {
        this.id = id;
        this.student = student;
        this.date = date;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StudentDto getStudent() { return student; }
    public void setStudent(StudentDto student) { this.student = student; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
