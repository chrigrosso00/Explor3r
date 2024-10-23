package com.explorer.services;

import java.util.List;
import java.util.Optional;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;

public interface PrenotazioneServices {
	List<Prenotazione> findAll();
	List<Prenotazione> findByUtente( Utente utente); 
	List<Prenotazione> findByViaggio(Viaggio viaggio); 
	Optional<Prenotazione> findById(PrenotazioneId id); 
	List<Prenotazione> findByData(String data); 
	int countByUtente(Utente utente); 
	Prenotazione addPrenotazione(Utente utente, Viaggio viaggio);
	void deletePrenotazione(int utenteId, int viaggioId);
}
