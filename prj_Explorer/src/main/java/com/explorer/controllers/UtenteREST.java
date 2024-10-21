package com.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Utente;
import com.explorer.services.EmailServices;
import com.explorer.services.UtenteServices;

@RestController
@RequestMapping("api")
public class UtenteREST {
	
	@Autowired
	private UtenteServices uService;
	
	/*@Autowired
	private EmailServices emailService;*/
	
	@GetMapping("utenti")
	public List<Utente> getUtente() {
		return uService.findAll();
	}
	
	@GetMapping("utente/codice/{id}")
	public ResponseEntity<Utente> getUtenteById(@PathVariable int id) {
	    Utente utente = uService.findById(id);
	    if (utente != null) {
	        return ResponseEntity.ok(utente);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@PostMapping(value = "/add/utente", consumes = "application/json")
	public ResponseEntity<Utente> addUtente(@RequestBody Utente u) {
	    try {
	        Utente nuovoUtente = uService.addUtente(u);
	        //emailService.sendEmail(u.getEmail(), "Benvenuto!", "Grazie per esserti registrato!");
	        return new ResponseEntity<Utente>(nuovoUtente, HttpStatus.CREATED);
	    } catch (MailException e) {
	        System.err.println("Errore durante l'invio dell'email: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    } catch (RuntimeException e) {
	        System.err.println("Errore durante la registrazione: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@DeleteMapping("utente/delete/{id}")
	public ResponseEntity<String> deleteUtente(@PathVariable int id) {
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
    
    @GetMapping("/utenti/nome/{nome}")
	public ResponseEntity<List<Utente>> getUtenteByNome(@PathVariable String nome) {
	    List<Utente> utenti = uService.findByNome(nome);
	    if (utenti != null && !utenti.isEmpty()) {
	        return ResponseEntity.ok(utenti);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
    
    @GetMapping("/utenti/cognomeNome/{nome}/{cognome}")
   	public ResponseEntity<List<Utente>> getUtenteByNomeCognome(@PathVariable String nome, @PathVariable String cognome) {
   	    List<Utente> utenti = uService.findByNomeCognome(nome, cognome);
   	    if (utenti != null && !utenti.isEmpty()) {
   	        return ResponseEntity.ok(utenti);
   	    } else {
   	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
   	    }
   	}
    
    @GetMapping("/utente/nominativo/{username}")
   	public ResponseEntity<Utente> getUtenteByUsername(@PathVariable String username) {
   	    Utente utente = uService.findByUsername(username);
   	    if (utente != null) {
   	        return ResponseEntity.ok(utente);
   	    } else {
   	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
   	    }
   	}
}

	
