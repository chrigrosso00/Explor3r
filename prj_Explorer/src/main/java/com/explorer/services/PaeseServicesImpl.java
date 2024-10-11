package com.explorer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.entities.Paese;
import com.explorer.repos.PaeseDAO;

@Service
public class PaeseServicesImpl implements PaeseServices{

	@Autowired
	private PaeseDAO dao;
	
	@Override
	public List<Paese> findAllPaesi() {
		return dao.findAll();
	}

}
