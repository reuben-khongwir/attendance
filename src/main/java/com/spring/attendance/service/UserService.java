
package com.spring.attendance.service;

import com.spring.attendance.model.AccountStatus;
import com.spring.attendance.model.User;
import com.spring.attendance.model.Role;
import com.spring.attendance.repository.UserRepository;
import com.spring.attendance.repository.RoleRepository;
import com.spring.attendance.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role userRole = roleRepository.findByName("TEACHER")
                .orElseGet(() -> roleRepository.save(new Role(null, "TEACHER")));

        user.setRoles(List.of(userRole));
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getStatus() != AccountStatus.APPROVED) {
            throw new org.springframework.security.authentication.DisabledException(
                    "User not approved. Current status: " + user.getStatus()
            );
        }
        return user;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email " + email);
//        }
//        return user;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
    public List<User> getAllTeachers() {
        return userRepository.findByRoles_NameAndStatus("TEACHER", AccountStatus.APPROVED);
    }

//    public Page<User> findPaginated(int pageNo, int pageSize){
//
//        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
//        return this.userRepository.findAll(pageable);
//    }
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }

    //2.
    public Page<User> findPaginatedTeachers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findByRoles_NameAndStatus("TEACHER", AccountStatus.APPROVED, pageable);
    }

}
