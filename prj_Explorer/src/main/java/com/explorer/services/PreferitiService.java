package com.explorer.services;

import java.util.List;

import com.explorer.entities.Preferiti;

public interface PreferitiService {

	public List<Preferiti> findAll();

	public boolean add(Preferiti p);
	

//	boolean remove(int idUtente, int idViaggio);
	
	void remove(Preferiti p);
}
