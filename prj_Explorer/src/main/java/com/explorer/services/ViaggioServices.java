package com.explor3r.services;

import java.util.List;

import com.explor3r.entities.Viaggio;

public interface ViaggioServices {
	List<Viaggio> findAll();
	Viaggio findById(int id);
	Viaggio save(Viaggio viaggio);
}
