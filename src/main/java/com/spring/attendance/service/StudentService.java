package com.spring.attendance.service;

import com.spring.attendance.model.Student;
import com.spring.attendance.repository.StudentRepository;
import com.spring.attendance.dto.StudentDto;
import com.spring.attendance.mapper.StudentMapper;
import com.spring.attendance.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService  {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ---------- For Teacher ----------
    public List<StudentDto> getAllStudentsByTeacher(Long teacherId) {
        return studentRepository.findByUserId(teacherId)
                .stream()
                .map(StudentMapper::mapToStudentDto)
                .toList();
    }

    public StudentDto updateStudentByTeacher(StudentDto dto, Long teacherId) {
        Student existing = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!existing.getUser().getId().equals(teacherId)) {
            throw new RuntimeException("Unauthorized edit attempt");
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());

        return StudentMapper.mapToStudentDto(studentRepository.save(existing));
    }




    // ---------- For Admin ----------
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::mapToStudentDto)
                .toList();
    }

    public StudentDto saveStudentByAdmin(StudentDto dto, Long teacherId) {
        Student student = StudentMapper.mapToStudent(dto);

        // link to teacher
        User teacher = new User();
        teacher.setId(teacherId);
        student.setUser(teacher);

        return StudentMapper.mapToStudentDto(studentRepository.save(student));
    }

    public StudentDto updateStudentByAdmin(StudentDto dto, Long teacherId) {
        Student existing = studentRepository.findById(dto.getId()).orElseThrow();
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());

        User teacher = new User();
        teacher.setId(teacherId);
        existing.setUser(teacher);

        return StudentMapper.mapToStudentDto(studentRepository.save(existing));
    }

    public void deleteStudentByAdmin(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentDto getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(StudentMapper::mapToStudentDto)
                .orElse(null);
    }
}
