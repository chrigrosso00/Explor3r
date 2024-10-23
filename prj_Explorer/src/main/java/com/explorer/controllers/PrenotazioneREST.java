package com.explorer.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.explorer.repos.ViaggioDAO;
import com.explorer.security.UserPrincipal;
import com.explorer.services.PrenotazioneServices;
import com.explorer.services.ViaggioServices;

@RestController
@RequestMapping("api")
public class PrenotazioneREST {
	
	@Autowired
	private PrenotazioneServices pService;
	
	@Autowired
	private UtenteDAO utentedao;
	
	@Autowired
	private ViaggioDAO viaggioDAO;
	
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
	public ResponseEntity<String> addPrenotazione(@RequestBody Viaggio viaggio) {
	    // 1. Ottieni l'utente autenticato
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // 2. Trova l'utente attuale dal database tramite il nome utente
	    Utente currentUser = utentedao.findByUsername(username).orElseThrow();

	    // 3. Recupera il viaggio dal database usando il suo ID
	    Viaggio viaggioPrenotazione = viaggioDAO.findById(viaggio.getId_viaggio()).orElse(null);
	    
	    // 4. Controlla se il viaggio esiste
	    if (viaggioPrenotazione == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viaggio non trovato");
	    }

	    // 5. Controlla se l'utente che ha creato il viaggio è lo stesso che sta tentando di prenotare
	    if (viaggioPrenotazione.getUtente().getId_utente() == currentUser.getId_utente()) {
	        // Se l'ID dell'utente creatore è uguale a quello dell'utente autenticato
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non puoi iscriverti al tuo viaggio");
	    }

	    // 6. Controlla se l'utente è già prenotato per lo stesso viaggio
	    Optional<Prenotazione> prenotazioneEsistente = pService.findById(new PrenotazioneId(currentUser.getId_utente(), viaggioPrenotazione.getId_viaggio()));

	    if (prenotazioneEsistente.isPresent()) {
	        // Se esiste già una prenotazione per questo utente e viaggio
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Sei già iscritto a questo viaggio");
	    }

	    // 7. Se l'utente non è il creatore e non è già iscritto, procedi con la prenotazione
	    Prenotazione nuovaPrenotazione = pService.addPrenotazione(currentUser, viaggioPrenotazione);
	    LocalDate today = LocalDate.now();  // Get today's date

	    // Convert LocalDate to java.sql.Date
	    Date formattedDate = Date.valueOf(today);  // This is a convenient way to convert LocalDate to java.sql.Date

	    nuovaPrenotazione.setData(formattedDate);  // Set the Date

	    return new ResponseEntity<>("Prenotazione effettuata con successo", HttpStatus.CREATED);
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
	
	@GetMapping("prenotazioni/nominativo/{username}")
	public ResponseEntity<List<Prenotazione>> getByUsernameUser(@PathVariable String username) {
	    List<Prenotazione> prenotazione = pService.findByUsernameUser(username);
	    if (prenotazione != null && !prenotazione.isEmpty()) {
	    	return ResponseEntity.ok(prenotazione);
	    } else {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
}