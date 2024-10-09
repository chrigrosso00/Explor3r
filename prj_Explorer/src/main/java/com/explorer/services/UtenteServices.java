package com.explorer.services;

import java.util.List;

import com.explorer.entities.Utente;

public interface UtenteServices {
	List<Utente> findAll();
	Utente findById(int id);
	Utente addUtente(Utente utente);
	void deleteUtente(int id);
	Utente updateCredenziali(int id, Utente utente);
	List<Utente> findByNome(String nome);
	List<Utente> findByNomeCognome(String nome, String cognome);
	
}

