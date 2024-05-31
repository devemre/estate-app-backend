package com.devemre.estateappbackend.controller;

import com.devemre.estateappbackend.controller.request.LoginRequest;
import com.devemre.estateappbackend.controller.request.RegisterRequest;
import com.devemre.estateappbackend.controller.response.LoginResponse;
import com.devemre.estateappbackend.entity.Authority;
import com.devemre.estateappbackend.entity.EstateUser;
import com.devemre.estateappbackend.repository.AuthorityRepository;
import com.devemre.estateappbackend.repository.EstateUserRepository;
import com.devemre.estateappbackend.service.AuthenticationService;
import com.devemre.estateappbackend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtService jwtService;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {

        EstateUser estateUser = authenticationService.signup(registerRequest);

        if (estateUser.getId() > 0) {
            return ResponseEntity.ok("User with email " + estateUser.getEmail() + " has been registered successfully with id " + estateUser.getId());
        }

        return ResponseEntity.badRequest().body("User with email " + estateUser.getEmail() + " could not be registered");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        EstateUser authenticatedUser = authenticationService.authenticate(loginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    public String determineRole(String role) {
        switch (role) {
            case "ADMIN":
                return "ROLE_ADMIN";
            case "USER":
                return "ROLE_USER";
            default:
                return "";
        }
    }
}
