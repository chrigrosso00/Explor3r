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
    public Utente save(Utente utente) {
        return dao.save(utente);
    }
}