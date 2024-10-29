package com.explorer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.explorer.controllers.LoginRequest;
import com.explorer.entities.Utente;
import com.explorer.entities.UserAuthority;
import com.explorer.entities.UserAuthorityId;
import com.explorer.repos.UtenteDAO;
import com.explorer.security.JwtTokenProvider;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UtenteDAO userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUser(LoginRequest loginRequest) {
        System.out.println("Attempting to authenticate user: " + loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful for user: " + loginRequest.getUsername());
            return tokenProvider.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("Authentication failed for user: " + loginRequest.getUsername() + " - " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }

    public void createAdminUser() {
        if (!userRepository.findByUsername("admin").isPresent()) {
            Utente admin = new Utente();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setCognome("min");
            admin.setNome("ad");
            Date date = Date.valueOf("1950-05-05");
            admin.setData_nascita(date);
            admin.setTelefono(0);
            admin.setEmail("admin@email.com");
            UserAuthority authority = new UserAuthority();
            UserAuthorityId authorityId = new UserAuthorityId();
            authorityId.setUserId(admin.getId_utente());
            authority.setId(authorityId);
            authority.setUser(admin);
            authority.setAuthority("ROLE_ADMIN");

            admin.setAuthorities(Set.of(authority));

            userRepository.save(admin);
        }
    }
}