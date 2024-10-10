package com.explorer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.repos.PaeseDAO;

@Service
public class PaeseServiceImpl implements PaeseService {

	@Autowired
	PaeseDAO dao;
	
	
}
