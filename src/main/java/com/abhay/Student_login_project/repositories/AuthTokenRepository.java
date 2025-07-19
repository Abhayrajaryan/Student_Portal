package com.abhay.Student_login_project.repositories;

import com.abhay.Student_login_project.entities.AuthToken;
import com.abhay.Student_login_project.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByStudent(Student student);
    Optional<AuthToken> findByToken(String token);
}
