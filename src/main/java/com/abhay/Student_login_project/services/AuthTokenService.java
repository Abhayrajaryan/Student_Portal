package com.abhay.Student_login_project.services;

import com.abhay.Student_login_project.entities.AuthToken;
import com.abhay.Student_login_project.entities.Student;
import com.abhay.Student_login_project.repositories.AuthTokenRepository;
import com.abhay.Student_login_project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthTokenService {

    @Autowired
    private AuthTokenRepository authTokenRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public String generateOrReuseToken(Student student) {
        Optional<AuthToken> optionalAuthToken = authTokenRepository.findByStudent(student);

        if (optionalAuthToken.isPresent()) {
            AuthToken existing = optionalAuthToken.get();

            if (!jwtUtil.isTokenExpired(existing.getToken())) {
                // Token is valid
                return existing.getToken();
            }

            // Token expired – delete old and create new
            authTokenRepository.delete(existing);
        }

        // Generate new token
        String newToken = jwtUtil.generateToken(student.getUsername());

        AuthToken newAuthToken = new AuthToken();
        newAuthToken.setToken(newToken);
        newAuthToken.setStudent(student);

        authTokenRepository.save(newAuthToken);

        return newToken;
    }

    public boolean logout(String token) {
        Optional<AuthToken> authToken = authTokenRepository.findByToken(token);
        if (authToken.isPresent()) {
            authTokenRepository.delete(authToken.get());
            return true;
        }
        return false;
    }
}

