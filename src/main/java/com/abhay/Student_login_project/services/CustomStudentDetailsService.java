package com.abhay.Student_login_project.services;

import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomStudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .roles("USER")
                .build();
    }
}
