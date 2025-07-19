package com.abhay.Student_login_project.repositories;

import com.abhay.Student_login_project.entities.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
}
