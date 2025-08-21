package com.spring.attendance.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Example: "STUDENT", "TEACHER", "ADMIN"

    @Override
    public String getAuthority() {
        return "ROLE_" + name; // Spring Security expects roles with "ROLE_" prefix
    }
}
