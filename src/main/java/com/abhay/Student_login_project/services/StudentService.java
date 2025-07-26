package com.abhay.Student_login_project.services;

import com.abhay.Student_login_project.dto.request.StudentRegisterDTO;
import com.abhay.Student_login_project.entities.Questions;
import com.abhay.Student_login_project.entities.SecurityQuestion;
import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.repositories.QuestionsRepository;
import com.abhay.Student_login_project.repositories.SecurityQuestionRepository;
import com.abhay.Student_login_project.repositories.StudentRepository;
import com.abhay.Student_login_project.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private Util util;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student registerStudent(StudentRegisterDTO dto) {
        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setEmail(dto.getEmail());
        student.setFirstName(dto.getFirstName());
        student.setMiddleName(dto.getMiddleName());
        student.setLastName(dto.getLastName());

        if (dto.getSecurityQuestionId() !=null) {
            Questions q = questionsRepository.findById(dto.getSecurityQuestionId())
                    .orElseThrow(() -> new RuntimeException("Security question not found"));

            SecurityQuestion sq = new SecurityQuestion();
            sq.setQuestion(q);
            sq.setAnswer(dto.getSecurityQuestionAnswer());
            student.setSecurityQuestion(sq);
        }

        return studentRepository.save(student);
    }

    public Student getStudentDetails(String authHeader) {
        return util.getStudentFromToken(authHeader);
    }
}
