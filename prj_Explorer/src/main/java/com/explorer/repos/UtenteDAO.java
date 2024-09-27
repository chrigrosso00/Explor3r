package com.explor3r.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explor3r.entities.Utente;

public interface UtenteDAO extends JpaRepository<Utente, Integer> {}