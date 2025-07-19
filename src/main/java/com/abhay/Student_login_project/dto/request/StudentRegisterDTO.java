package com.abhay.Student_login_project.dto.request;

import lombok.Data;

@Data
public class StudentRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer securityQuestionId;
    private String securityQuestionAnswer;
}
