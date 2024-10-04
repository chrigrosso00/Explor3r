package com.explorer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explorer.entities.Preferiti;
import com.explorer.entities.PreferitiId;

public interface PreferitiDAO extends JpaRepository<Preferiti, PreferitiId> {

}
