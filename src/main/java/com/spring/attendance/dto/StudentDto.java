package com.spring.attendance.dto;


public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String teacherName;
    public StudentDto() {}



    public StudentDto(Long id, String firstName, String lastName, String email,String teacherName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.teacherName=teacherName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTeacherName() {return teacherName;}

    public void setTeacherName(String teacherName) {this.teacherName = teacherName;}
}
