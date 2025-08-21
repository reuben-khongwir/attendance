package com.spring.attendance.repository;

import com.spring.attendance.model.Attendance;
import com.spring.attendance.model.Student;
import com.spring.attendance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {



    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByStudentIdInAndDateBetween(List<Long> studentIds, LocalDate start, LocalDate end);

    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);
}
