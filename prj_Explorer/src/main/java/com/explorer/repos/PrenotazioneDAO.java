package com.explorer.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;

public interface PrenotazioneDAO extends JpaRepository<Prenotazione, PrenotazioneId> {
	
	 @Query("SELECT p FROM Prenotazione p WHERE p.utente = :utente")
	List<Prenotazione> findByUtente(@Param("utente") Utente utente); 
	
	@Query("SELECT p FROM Prenotazione p WHERE p.viaggio = :viaggio")
	List<Prenotazione> findByViaggio(@Param("viaggio") Viaggio viaggio); 
	
	@Query("SELECT p FROM Prenotazione p WHERE p.id = :id")
	Optional<Prenotazione> findById(@Param("id") PrenotazioneId id); 
	
	@Query("SELECT p FROM Prenotazione p WHERE p.data = :data")
	List<Prenotazione> findByData(@Param("data") String data); 
	
	@Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.utente = :utente")
	int countByUtente(@Param("utente") Utente utente);
	
	@Query("SELECT p FROM Prenotazione p WHERE p.utente.username = :username")
	List<Prenotazione> findPrenotazioniByUsernameUser(String username);
}