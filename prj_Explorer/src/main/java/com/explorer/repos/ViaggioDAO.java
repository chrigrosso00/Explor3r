package com.explorer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explorer.entities.Viaggio;

public interface ViaggioDAO extends JpaRepository<Viaggio, Integer> {}

