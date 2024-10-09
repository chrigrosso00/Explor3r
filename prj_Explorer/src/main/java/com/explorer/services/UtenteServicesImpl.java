package com.explorer.services;

import com.explorer.entities.Utente;
import com.explorer.repos.UtenteDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteServicesImpl implements UtenteServices {
    @Autowired
    private UtenteDAO dao;

    @Override
    public List<Utente> findAll() {
        return dao.findAll();
    }

    @Override
    public Utente findById(int id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Utente addUtente(Utente utente) {
        return dao.save(utente);
    }
    
    @Override
    public void deleteUtente(int id) {
        dao.deleteById(id);
    }

    @Override
    public Utente updateCredenziali(int id, Utente user) {
        return dao.findById(id).map(utente -> {
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