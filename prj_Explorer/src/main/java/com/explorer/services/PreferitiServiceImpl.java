package com.explorer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.explorer.entities.Preferiti;
import com.explorer.repos.PreferitiDAO;

@Service
public class PreferitiServiceImpl implements PreferitiService {
	
	PreferitiDAO dao;

	@Override
	public List<Preferiti> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	
	
}
