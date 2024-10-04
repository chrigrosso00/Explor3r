package com.explorer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;

public interface PrenotazioneDAO extends JpaRepository<Prenotazione, PrenotazioneId> {

}
