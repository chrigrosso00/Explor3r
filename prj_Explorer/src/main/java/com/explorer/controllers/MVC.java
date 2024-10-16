package com.explorer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVC {
	
	@GetMapping("index")
	public String index() {
		return "index"; 
	}
	
	@GetMapping("registrazione")
	public String registrazione() {
		return "registrazione"; 
	}
	
	@GetMapping("viaggi")
	public String viaggi() {
		return "viaggi"; 
	}
}

