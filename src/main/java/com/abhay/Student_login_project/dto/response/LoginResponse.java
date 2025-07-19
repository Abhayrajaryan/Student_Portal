package com.abhay.Student_login_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private String token;
    private String email;
    private String fullName;
}

