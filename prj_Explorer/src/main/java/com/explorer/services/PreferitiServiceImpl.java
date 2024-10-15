package com.explorer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.entities.Preferiti;
import com.explorer.repos.PreferitiDAO;

@Service
public class PreferitiServiceImpl implements PreferitiService {
	
	@Autowired
	PreferitiDAO dao;

	@Override
	public List<Preferiti> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	
	@Override
	public boolean add(Preferiti p) {
		if(dao.save(p) == p) {
			return true;
		}
		return false;
	}
	
	
}
