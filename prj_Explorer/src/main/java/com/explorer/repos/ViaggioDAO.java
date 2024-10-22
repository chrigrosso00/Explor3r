package com.explorer.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.explorer.entities.Viaggio;

public interface ViaggioDAO extends JpaRepository<Viaggio, Integer> {
	
	@Query("SELECT v FROM Viaggio v WHERE v.paese.continente = :continente")
    List<Viaggio> findByContinente(String continente);
	
	@Query("SELECT v FROM Viaggio v WHERE v.paese.stato = :stato")
    List<Viaggio> findByStato(String stato);
	
	List<Viaggio> findByTipologia(String tipologia);
	
	@Query("SELECT v FROM Viaggio v WHERE v.paese.stato = :stato AND v.data_Partenza = :dataPartenza")
	List<Viaggio> findByStatoAndDataPartenza(String stato, LocalDate dataPartenza);
	
}

