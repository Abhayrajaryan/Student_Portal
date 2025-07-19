package com.abhay.Student_login_project.controllers;

import com.abhay.Student_login_project.dto.request.StudentRegisterDTO;
import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody StudentRegisterDTO dto) {
        Student student = studentService.registerStudent(dto);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getStudentDetails(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Student student = studentService.getStudentDetails(authHeader);
        return ResponseEntity.ok(student);
    }
}
