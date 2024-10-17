package com.explorer.services;

import com.explorer.entities.UserAuthority;
import com.explorer.entities.UserAuthorityId;
import com.explorer.entities.Utente;
import com.explorer.repos.UserAuthorityDAO;
import com.explorer.repos.UtenteDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteServicesImpl implements UtenteServices {
    @Autowired
    private UtenteDAO dao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserAuthorityDAO userAuthorityDAO;

    @Override
    public List<Utente> findAll() {
        return dao.findAll();
    }
    @Override
    public Utente findById(int id) {
        return dao.findById((long) id).orElse(null);
    }
    @Override
    public Utente addUtente(Utente utente) {
    	utente.setPassword(passwordEncoder.encode(utente.getPassword()));
    	Utente savedUtente = dao.save(utente);

    	UserAuthority userAuthority = new UserAuthority();
        UserAuthorityId userAuthorityId = new UserAuthorityId();
        userAuthorityId.setUserId(savedUtente.getId_utente());
        userAuthority.setId(userAuthorityId);
        userAuthority.setUser(savedUtente);
        userAuthority.setAuthority("ROLE_USER");

        userAuthorityDAO.save(userAuthority);

        return savedUtente;
    }
    @Override
    public void deleteUtente(int id) {
        dao.deleteById((long) id);
    }
    @Override
    public Utente updateCredenziali(int id, Utente user) {
        return dao.findById((long) id).map(utente -> {
            utente.setUsername(user.getUsername());
            utente.setPassword(user.getPassword());
            return dao.save(utente);
        }).orElse(null);
    }
    @Override
    public List<Utente> findByNome(String nome) {
        return dao.findByNome(nome);
    }
    @Override
    public List<Utente> findByNomeCognome(String nome, String cognome) {
        return dao.findByNomeAndCognome(nome, cognome);
    }
}