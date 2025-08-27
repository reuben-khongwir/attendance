package com.spring.attendance.repository;

import com.spring.attendance.model.AccountStatus;
import com.spring.attendance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByRoles_Name(String roleName);

    List<User> findByRoles_NameAndStatus(String roleName, AccountStatus status);

    Page<User> findByRoles_NameAndStatus(String roleName, AccountStatus status, Pageable pageable);

}
