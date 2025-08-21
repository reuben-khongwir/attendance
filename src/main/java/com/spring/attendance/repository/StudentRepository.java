package com.spring.attendance.repository;


import com.spring.attendance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByUserId(Long userId);

}

