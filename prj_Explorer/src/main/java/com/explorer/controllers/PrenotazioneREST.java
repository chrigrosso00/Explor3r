package com.explorer.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.repos.UtenteDAO;
import com.explorer.security.UserPrincipal;
import com.explorer.services.PrenotazioneServices;

@RestController
@RequestMapping("api")
public class PrenotazioneREST {
	
	@Autowired
	private PrenotazioneServices pService;
	
	@Autowired
	private UtenteDAO utentedao;
	
	@GetMapping("prenotazioni")
	public List<Prenotazione> getPrenotazione() {
		return pService.findAll();
	}
	
	@GetMapping("prenotazioni/utente/{utenteId}")
	public ResponseEntity<List<Prenotazione>> getPrenotazioneByUtente(@PathVariable int utenteId) {
		Utente utente = new Utente();
	    utente.setId_utente(utenteId);
		List<Prenotazione> prenotazioni = pService.findByUtente(utente);
	    if (prenotazioni != null && !prenotazioni.isEmpty()) {
	        return ResponseEntity.ok(prenotazioni);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@GetMapping("prenotazioni/viaggio/{viaggioId}")
	public ResponseEntity<List<Prenotazione>> getPrenotazioneByViaggio(@PathVariable int viaggioId) {
	    Viaggio viaggio = new Viaggio();
        viaggio.setId_viaggio(viaggioId);
		List<Prenotazione> prenotazioni = pService.findByViaggio(viaggio);
	    if (prenotazioni != null && !prenotazioni.isEmpty()) {
	        return ResponseEntity.ok(prenotazioni);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@GetMapping("prenotazione/codice/{id}")
	public ResponseEntity<Optional<Prenotazione>> getPrenotazioneById(@PathVariable PrenotazioneId id) {
	    Optional<Prenotazione> prenotazione = pService.findById(id);
	    if (prenotazione.isPresent()) {
	        return ResponseEntity.ok(prenotazione);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
	    }
	}
	
	@GetMapping("prenotazioni/codice/{data}")
	public ResponseEntity<List<Prenotazione>> getPrenotazioneByData(@PathVariable String data) {
	    List<Prenotazione> prenotazioni = pService.findByData(data);
	    if (prenotazioni != null && !prenotazioni.isEmpty()) {
	        return ResponseEntity.ok(prenotazioni);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@GetMapping("prenotazione/utente/totale/{utenteId}")
	public ResponseEntity<Integer> getTotalePrenotazioniByUtente(@PathVariable int utenteId) {
		Utente utente = new Utente();
	    utente.setId_utente(utenteId);
	    int quantita = pService.countByUtente(utente);
        if (quantita >= 0) {
            return ResponseEntity.ok(quantita);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	@PostMapping("prenotazione")
	public ResponseEntity<Prenotazione> addPrenotazione(@RequestBody Viaggio viaggio) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        Utente currentUser = utentedao.findByUsername(username).orElseThrow();
		return new ResponseEntity<Prenotazione>(pService.addPrenotazione(currentUser, viaggio), HttpStatus.CREATED);
	}
	
	@DeleteMapping("prenotazione/delete/{utenteId}/{viaggioId}")
	public ResponseEntity<String> deletePrenotazione(@PathVariable int utenteId, @PathVariable int viaggioId) {
		PrenotazioneId id = new PrenotazioneId();
	    id.setUtenteId(utenteId);
	    id.setViaggioId(viaggioId);
	    Optional<Prenotazione> prenotazione = pService.findById(id);
        if (prenotazione.isPresent()) {
            pService.deletePrenotazione(utenteId, viaggioId);
            return ResponseEntity.ok("Prenotazione eliminata con successo.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prenotazione non trovata.");
        }
    }
}
