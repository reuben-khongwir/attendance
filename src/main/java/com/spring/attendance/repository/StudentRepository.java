package com.spring.attendance.repository;


import com.spring.attendance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByUserId(Long userId);

    Page<Student> findByUserId(Long userId, Pageable pageable);

}

