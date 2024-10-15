package com.explorer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Preferiti;
import com.explorer.entities.Utente;
import com.explorer.services.PreferitiServiceImpl;
import com.explorer.services.UtenteServicesImpl;
import com.explorer.services.ViaggioServicesImpl;

@RestController
@RequestMapping("api")
public class PreferitiREST {

	@Autowired
	PreferitiServiceImpl preferitiService;
	
	@Autowired
	UtenteServicesImpl utenteService;
	
	@Autowired
	ViaggioServicesImpl viaggioService;
	
	@GetMapping("/preferiti/{utenteId}")
	public ResponseEntity<List<Preferiti>> getPreferitiByUtente(@PathVariable int utenteId) {
		if(utenteService.findById(utenteId) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		List<Preferiti> all = preferitiService.findAll();
		List<Preferiti> preferitiUtente = new ArrayList<>();
		
		for (Preferiti preferiti : all) {
			if(preferiti.getUtente().getId_utente() == utenteId) {
				preferitiUtente.add(preferiti);
			}
		}
		
		return ResponseEntity.ok(preferitiUtente);

    }
	
	@PostMapping("/nuovoPreferito/{utenteId}/{viaggioId}")
	public ResponseEntity<Boolean> addPreferito(@PathVariable int utenteId, @PathVariable int viaggioId){
		Preferiti preferito = new Preferiti();
		
		if(utenteService.findById(utenteId) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		if(viaggioService.findById(viaggioId) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		preferito.setUtente(utenteService.findById(utenteId));
		preferito.setViaggio(viaggioService.findById(viaggioId));
		boolean ris = preferitiService.add(preferito);
		
		
		
		return ResponseEntity.ok(ris);
	}
	
	@PutMapping("/rimozione")
	public ResponseEntity<Boolean> removePreferito(@RequestBody Preferiti p){
		preferitiService.remove(p);
		return ResponseEntity.ok(true);
		
	}
}

