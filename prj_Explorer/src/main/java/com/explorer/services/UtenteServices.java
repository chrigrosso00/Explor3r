package com.explorer.services;

import java.util.List;

import com.explorer.entities.Utente;

public interface UtenteServices {
	List<Utente> findAll();
	Utente findById(int id);
	Utente save(Utente utente);
}

