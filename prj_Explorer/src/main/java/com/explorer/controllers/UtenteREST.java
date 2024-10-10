package com.explorer.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Utente;
import com.explorer.services.UtenteServices;

@RestController
@RequestMapping("api")
public class UtenteREST {
	
	@Autowired
	private UtenteServices uService;
	
	@GetMapping("utente")
	public List<Utente> getUtente() {
		return uService.findAll();
	}
	
	@GetMapping("utente/codice/{id}")
	public ResponseEntity<Utente> getUtenteById(@PathVariable Integer id) {
	    Utente utente = uService.findById(id);
	    if (utente != null) {
	        return ResponseEntity.ok(utente);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@PostMapping("utente")
	public ResponseEntity<Utente> addUtente(@RequestBody Utente u) {
		return new ResponseEntity<Utente>( uService.addUtente(u), HttpStatus.CREATED);
	}
	
	@GetMapping("utente/delete/{id}")
	public ResponseEntity<String> deleteUtente(@RequestParam int id) {
        Utente utente = uService.findById(id);
        if (utente != null) {
            uService.deleteUtente(id);
            return ResponseEntity.ok("Utente eliminato con successo.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato.");
        }
    }
	
    @PutMapping("utente/credenziali/{id}")
    public ResponseEntity<Utente> updateCredenziali(@PathVariable int id, @RequestBody Utente user) {
        Utente updatedUtente = uService.updateCredenziali(id, user);
        if (updatedUtente != null) {
            return ResponseEntity.ok(updatedUtente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping("/utente/nome/{nome}")
	public ResponseEntity<List<Utente>> getUtenteByNome(@PathVariable String nome) {
	    List<Utente> utenti = uService.findByNome(nome);
	    if (utenti != null && !utenti.isEmpty()) {
	        return ResponseEntity.ok(utenti);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
    
    @GetMapping("/utente/cognomeNome/{nome}/{cognome}")
   	public ResponseEntity<List<Utente>> getUtenteByNomeCognome(@PathVariable String nome, @PathVariable String cognome) {
   	    List<Utente> utenti = uService.findByNomeCognome(nome, cognome);
   	    if (utenti != null && !utenti.isEmpty()) {
   	        return ResponseEntity.ok(utenti);
   	    } else {
   	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
   	    }
   	}
    
    
}

	
