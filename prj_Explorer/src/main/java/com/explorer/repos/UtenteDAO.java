package com.explorer.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explorer.entities.Utente;


public interface UtenteDAO extends JpaRepository<Utente,Long> {
	
	@Query("SELECT u FROM Utente u WHERE u.nome = :nome")
	List<Utente> findByNome(@Param("nome") String nome);
	    
	@Query("SELECT u FROM Utente u WHERE u.nome = :nome AND u.cognome = :cognome")
	List<Utente> findByNomeAndCognome(@Param("nome") String nome, @Param("cognome") String cognome);
	
	Optional<Utente> findByUsername(String username);
}