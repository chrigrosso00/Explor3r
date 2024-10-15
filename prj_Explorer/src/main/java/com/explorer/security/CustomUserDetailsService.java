package com.explorer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.explorer.entities.CustomUserDetails;
import com.explorer.entities.Utente;
import com.explorer.repos.UtenteDAO;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteDAO utenteDAO; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(utente.getUsername(), utente.getPassword());
    }
}
