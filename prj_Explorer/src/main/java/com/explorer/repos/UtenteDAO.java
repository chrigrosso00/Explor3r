package com.explorer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explorer.entities.Utente;

public interface UtenteDAO extends JpaRepository<Utente,Integer> {}