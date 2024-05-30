package com.devemre.estateappbackend.config;

import com.devemre.estateappbackend.entity.Authority;
import com.devemre.estateappbackend.entity.EstateUser;
import com.devemre.estateappbackend.repository.EstateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class EstateAuthenticationProvider implements AuthenticationProvider {

    private EstateUserRepository estateUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EstateAuthenticationProvider(EstateUserRepository estateUserRepository, PasswordEncoder passwordEncoder) {
        this.estateUserRepository = estateUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        EstateUser estateUser = estateUserRepository.findByEmail(email);
        if (estateUser == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        else if (passwordEncoder.matches(password, estateUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password, getGrantedAuthorities(estateUser.getAuthorities()));
        }
        else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
