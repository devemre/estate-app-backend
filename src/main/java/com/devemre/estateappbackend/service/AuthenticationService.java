package com.devemre.estateappbackend.service;

import com.devemre.estateappbackend.controller.request.LoginRequest;
import com.devemre.estateappbackend.controller.request.RegisterRequest;
import com.devemre.estateappbackend.entity.Authority;
import com.devemre.estateappbackend.entity.EstateUser;
import com.devemre.estateappbackend.exception.PasswordsDoNotMatchException;
import com.devemre.estateappbackend.exception.RegisterNotSuccessfulException;
import com.devemre.estateappbackend.exception.RoleNotFoundException;
import com.devemre.estateappbackend.exception.UserAlreadyExistsException;
import com.devemre.estateappbackend.repository.AuthorityRepository;
import com.devemre.estateappbackend.repository.EstateUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final EstateUserRepository estateUserRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            EstateUserRepository estateUserRepository,
            AuthorityRepository authorityRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.estateUserRepository = estateUserRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EstateUser signup(RegisterRequest registerRequest) {
        String role = determineRole(registerRequest.getRole());

        if (role == null) {
            throw new RoleNotFoundException("Role does not exist!");
        }
        else if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new PasswordsDoNotMatchException("Passwords do not match!");
        }

        EstateUser estateUser = new EstateUser();
        estateUser.setEmail(registerRequest.getEmail());
        estateUser.setName(registerRequest.getName());
        estateUser.setMobileNumber(registerRequest.getMobileNumber());
        estateUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        estateUser.setCreateDt(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        estateUser.setActive(true);

        EstateUser savedUser = estateUserRepository.save(estateUser);

        if (savedUser.getId() == 0) {
            throw new RegisterNotSuccessfulException("User cannot be saved!");
        }

        Authority authority = new Authority();
        authority.setName(role);
        authority.setEstateUser(estateUser);
        authorityRepository.save(authority);

        return savedUser;
    }

    public EstateUser authenticate(LoginRequest loginRequest) {
        Optional<EstateUser> estateUser = estateUserRepository.findByEmail(loginRequest.getEmail());

        if (!estateUser.isPresent()) {
            throw new RuntimeException("Username or Password is Wrong!");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return estateUser.get();
    }

    public String determineRole(String role) {
        HashMap<String, String> roleMap = new HashMap<String, String>();
        roleMap.put("ADMIN", "ROLE_ADMIN");
        roleMap.put("USER", "ROLE_USER");
        return roleMap.get(role);
    }
}
