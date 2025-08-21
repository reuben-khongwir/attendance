package com.spring.attendance.service;

import com.spring.attendance.dto.TeacherDto;
import com.spring.attendance.dto.UserApprovalDto;
import com.spring.attendance.model.AccountStatus;
import com.spring.attendance.model.User;
import com.spring.attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    // Get all teachers with PENDING status
    public List<User> getPendingTeachers() {
        return userRepository.findByRoles_NameAndStatus("TEACHER", AccountStatus.PENDING);
    }

    // Approve or reject a teacher
    public void updateTeacherStatus(UserApprovalDto dto) {
        User teacher = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setStatus(dto.getStatus());
        userRepository.save(teacher);
    }
    // Get a single teacher by ID
    public User getTeacher(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    // Update teacher info
    public void updateTeacher(TeacherDto dto) {
        User teacher = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());
        teacher.setStatus(dto.getStatus());
        userRepository.save(teacher);
    }

    // Delete teacher
    public void deleteTeacher(Long id) {
        userRepository.deleteById(id);
    }

}
