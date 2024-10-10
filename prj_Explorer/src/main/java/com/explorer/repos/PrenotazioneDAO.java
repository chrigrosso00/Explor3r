package com.explorer.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;

public interface PrenotazioneDAO extends JpaRepository<Prenotazione, PrenotazioneId> {
	
	/*List<Prenotazione> findByUtente(Utente utente); //Trova tutte le prenotazioni di un utente
	List<Prenotazione> findByViaggio(Viaggio viaggio); // Trova tutte le prenotazioni di un viaggio
	Optional<Prenotazione> findById(PrenotazioneId id); //Trova una prenotazione specifica per utente e viaggio
	List<Prenotazione> findByData(String data); //Trova tutte le prenotazioni in una data specifica
	long countByUtente(Utente utente); //Conta il numero di prenotazioni per un utente
	void deleteAllByUtente(Utente utente); //Elimina tutte le prenotazioni di un utente
	void deleteByUtente(int utenteId, int viaggioId); //Elimina una prenotazione per un utente
	Prenotazione addPrenotazione(Prenotazione prenotazione); //Crea una prenotazione per un utente*/
}