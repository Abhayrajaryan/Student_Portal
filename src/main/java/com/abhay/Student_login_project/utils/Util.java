package com.abhay.Student_login_project.utils;

import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Util {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StudentRepository studentRepository;

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    public Student getStudentFromToken(String authHeader) {
        String token = extractToken(authHeader);
        String username = jwtUtil.extractUsername(token);

        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
    }
}
