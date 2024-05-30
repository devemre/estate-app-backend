package com.devemre.estateappbackend.controller;

import com.devemre.estateappbackend.controller.request.RegisterRequest;
import com.devemre.estateappbackend.entity.Authority;
import com.devemre.estateappbackend.entity.EstateUser;
import com.devemre.estateappbackend.repository.AuthorityRepository;
import com.devemre.estateappbackend.repository.EstateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class AuthController {

    private EstateUserRepository estateUserRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(EstateUserRepository estateUserRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.estateUserRepository = estateUserRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        if (estateUserRepository.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User with email " + registerRequest.getEmail() + " already exists");
        }

        if (selectRole(registerRequest.getRole()).equals("")) {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        EstateUser estateUser = new EstateUser();
        estateUser.setEmail(registerRequest.getEmail());
        estateUser.setName(registerRequest.getName());
        estateUser.setMobileNumber(registerRequest.getMobileNumber());
        estateUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        estateUser.setCreateDt(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        estateUser.setActive(true);

        EstateUser savedUser = estateUserRepository.save(estateUser);

        if (savedUser.getId() > 0) {
            Authority authority = new Authority();
            authority.setName(selectRole(registerRequest.getRole()));
            authority.setEstateUser(savedUser);
            authorityRepository.save(authority);
            return ResponseEntity.ok("User with email " + estateUser.getEmail() + " has been registered successfully with id " + estateUser.getId() + " and with role " + authority.getName());
        }

        return ResponseEntity.badRequest().body("User with email " + estateUser.getEmail() + " could not be registered");
    }

    public String selectRole(String role) {
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
