package com.explor3r.services;

import java.util.List;

import com.explor3r.entities.Utente;

public interface UtenteServices {
	List<Utente> findAll();
	Utente findById(int id);
	Utente save(Utente utente);
}

