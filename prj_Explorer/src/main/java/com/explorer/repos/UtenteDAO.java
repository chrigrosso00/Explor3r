package com.explorer.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.explorer.entities.Utente;

public interface UtenteDAO extends JpaRepository<Utente,Integer> {
	
    @Query("SELECT u.nome AS nome FROM Utente u")
 	List<Utente> findByNome(String nome);
	
    @Query("SELECT u.nome AS nome, u,cognome AS cognome FROM Utente u")
	List<Utente> findByNomeAndCognome(String nome, String cognome);
}