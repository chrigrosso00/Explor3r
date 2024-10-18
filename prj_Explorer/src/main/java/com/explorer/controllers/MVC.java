package com.explorer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVC {
	
	@GetMapping("")
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
	
	@GetMapping("login")
    public String login() {
		return "login"; 
	}
	
	@GetMapping("viaggiContinente")
	public String viaggiContinente() {
		return "viaggiContinente"; 
	}
	
	@GetMapping("viaggio")
	public String viaggio() {
		return "viaggio"; 
	}
	
	@GetMapping("viaggio/{id}")
	public String viaggioId() {
		return "viaggio"; 
	}
	
}

