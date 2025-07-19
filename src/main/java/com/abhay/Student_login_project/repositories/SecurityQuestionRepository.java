package com.abhay.Student_login_project.repositories;

import com.abhay.Student_login_project.entities.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {
}
