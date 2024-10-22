package com.explorer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.explorer.entities.UserAuthority;
import com.explorer.entities.UserAuthorityId;
import com.explorer.entities.Utente;
import com.explorer.repos.UserAuthorityDAO;
import com.explorer.repos.UtenteDAO;

@Service
public class UtenteServicesImpl implements UtenteServices {

    @Autowired
    private UtenteDAO utenteDAO; // Iniezione del DAO

    @Autowired
    private PasswordEncoder passwordEncoder; // Iniezione del PasswordEncoder
    
    @Autowired
    private UserAuthorityDAO userAuthorityDAO;

    @Override
    public List<Utente> findAll() {
        return utenteDAO.findAll();
    }

    @Override
    public Utente findById(int id) {
        return utenteDAO.findById(id).orElse(null); // Gestisci il caso in cui non esista
    }

    @Override
    public Utente addUtente(Utente utente) {
        // Cripta la password prima di salvare l'utente
        String encodedPassword = passwordEncoder.encode(utente.getPassword());
        utente.setPassword(encodedPassword); // Imposta la password criptata
        Utente savedUtente = utenteDAO.save(utente);
        
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
        utenteDAO.deleteById(id); // Converti l'id in Long
    }

    @Override
    public Utente updateCredenziali(int id, Utente utente) {
        // Prima cerca l'utente esistente
        Utente utenteEsistente = utenteDAO.findById(id).orElse(null);
        if (utenteEsistente != null) {
            // Aggiorna le credenziali
            utenteEsistente.setNome(utente.getNome());
            utenteEsistente.setCognome(utente.getCognome());
            // Se è stata fornita una nuova password, criptala e impostala
            if (utente.getPassword() != null && !utente.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(utente.getPassword());
                utenteEsistente.setPassword(encodedPassword);
            }
            return utenteDAO.save(utenteEsistente); // Salva l'utente aggiornato
        }
        return null; // Restituisce null se l'utente non esiste
    }

    @Override
    public List<Utente> findByNome(String nome) {
        return utenteDAO.findByNome(nome);
    }

    @Override
    public List<Utente> findByNomeCognome(String nome, String cognome) {
        return utenteDAO.findByNomeAndCognome(nome, cognome);
    }

    @Override
    public Utente findByUsername(String username) {
        return utenteDAO.findByUsername(username).orElse(null); // Gestisci il caso in cui non esista
    }
}
