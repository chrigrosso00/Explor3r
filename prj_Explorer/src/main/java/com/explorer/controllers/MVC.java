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
	
	@GetMapping("crea-viaggio")
    public String creaViaggio() {
        return "crea-viaggio"; // Serves crea-viaggio.html
    }
	
	@GetMapping("profilo")
    public String profilo() {
        return "profilo";
    }
	
	@GetMapping("risultati")
    public String risultati() {
        return "risultati";
    }	
	
	@GetMapping("mission")
	public String mission() {
		return "mission";
	}
	
	@GetMapping("contatti")
	public String contatti() {
		return "contatti";
	}
	
}

