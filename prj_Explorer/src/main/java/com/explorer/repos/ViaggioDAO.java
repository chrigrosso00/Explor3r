package com.explor3r.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explor3r.entities.Viaggio;

public interface ViaggioDAO extends JpaRepository<Viaggio, Integer> {}

