package com.explorer.services;

import java.util.List;

import com.explorer.entities.Viaggio;

public interface ViaggioServices {
	List<Viaggio> findAll();
	Viaggio findById(int id);
	Viaggio save(Viaggio viaggio);
}
