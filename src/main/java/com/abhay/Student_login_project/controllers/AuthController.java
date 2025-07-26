package com.abhay.Student_login_project.controllers;

import com.abhay.Student_login_project.dto.request.LoginRequest;
import com.abhay.Student_login_project.dto.response.LoginResponse;
import com.abhay.Student_login_project.entities.AuthToken;
import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.repositories.AuthTokenRepository;
import com.abhay.Student_login_project.repositories.StudentRepository;
import com.abhay.Student_login_project.services.AuthTokenService;
import com.abhay.Student_login_project.services.CustomStudentDetailsService;
import com.abhay.Student_login_project.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomStudentDetailsService userDetailsService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthTokenService authTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 1. Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Get Student entity
        Student student = studentRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        // 3. Generate token (reuse or regenerate)
        String token = authTokenService.generateOrReuseToken(student);

        // 4. Return response
        return ResponseEntity.ok(new LoginResponse(student.getUsername(),token, student.getEmail(), student.getFullName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
        }

        String token = header.substring(7); // remove "Bearer "

        boolean removed = authTokenService.logout(token);

        if (removed) {
            return ResponseEntity.ok("User logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found or already invalid");
        }
    }
}
