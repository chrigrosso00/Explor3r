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
	
	@Query("SELECT v FROM Viaggio v WHERE v.utente.username = :username")
	List<Viaggio> findViaggiByUsernameUser(String username);
	
	@Query("SELECT v FROM Viaggio v WHERE v.paese.stato = :stato AND v.tipologia = :tipologia")
	List<Viaggio> findByStatoAndTipologia(String stato, String tipologia);

	@Query("SELECT v FROM Viaggio v WHERE v.tipologia = :tipologia AND v.data_Partenza = :dataPartenza")
	List<Viaggio> findByTipologiaAndDataPartenza(String tipologia, LocalDate dataPartenza);

	@Query("SELECT v FROM Viaggio v WHERE v.paese.stato = :stato AND v.tipologia = :tipologia AND v.data_Partenza = :dataPartenza")
	List<Viaggio> findByStatoTipologiaAndDataPartenza(String stato, String tipologia, LocalDate dataPartenza);
	
	@Query("SELECT v FROM Viaggio v WHERE v.data_Partenza >= :dataPartenza") // Usa >= per includere la data
	List<Viaggio> findByDataPartenza(LocalDate dataPartenza);
	
	@Query(value = "SELECT * FROM ft_viaggio WHERE data_partenza >= CURDATE() ORDER BY data_partenza ASC LIMIT 3", nativeQuery = true)
	List<Viaggio> findViaggiInPartenza();


}

