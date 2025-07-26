package com.abhay.Student_login_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "security_question_id", unique = true)
    private SecurityQuestion securityQuestion;

    public String getFullName(){
        StringBuilder fullName = new StringBuilder();
        if(this.firstName != null && !this.firstName.equals("")){
            fullName.append(this.firstName);
        }
        if(this.middleName != null && !this.middleName.trim().equals("")){
            fullName.append(" ").append(this.middleName);
        }
        if(this.lastName != null && !this.lastName.trim().equals("")){
            fullName.append(" ").append(this.lastName);
        }
        return fullName.toString();
    }
}
