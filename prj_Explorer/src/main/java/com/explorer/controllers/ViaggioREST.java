package com.explorer.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Paese;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.services.PaeseServices;
import com.explorer.services.PrenotazioneServices;
import com.explorer.services.UtenteServices;
import com.explorer.services.ViaggioServices;

@RestController
@RequestMapping("api")
public class ViaggioREST {

    @Autowired
    private ViaggioServices viaggioServices;
    
    @Autowired
    private PaeseServices paeseServices;
    
    @Autowired
    private UtenteServices utenteServices;
    
    @Autowired
    private PrenotazioneServices prenoServices;

    // Trova tutti i viaggi
    @GetMapping("viaggi")
    public ResponseEntity<List<Viaggio>> getAllViaggi() {
        List<Viaggio> viaggi = viaggioServices.findAll();

        if (!viaggi.isEmpty()) {
            return ResponseEntity.ok(viaggi); // restituisce 200 e la lista di tutti i viaggi
        } else {
            return ResponseEntity.noContent().build(); // restituisce 204 se la lista è vuota
        }
    }

    // Trova un viaggio per id
    @GetMapping("viaggi/{id}")
    public ResponseEntity<Viaggio> getViaggioById(@PathVariable int id) {
        Viaggio viaggio = viaggioServices.findById(id);

        if (viaggio != null) {
            return ResponseEntity.ok(viaggio);
        } else {
            return ResponseEntity.notFound().build(); // restituisce 404 se il viaggio non esiste
        }
    }

    // Trova i viaggi in base al continente
    @GetMapping("/viaggi/continente")
    public ResponseEntity<List<Viaggio>> getViaggiByContinente(@RequestParam String continente) {
        List<Viaggio> viaggi = viaggioServices.getViaggiByContinente(continente);

        if (viaggi.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 se non ci sono viaggi
        } else {
            return ResponseEntity.ok(viaggi);
        }
    }

    @GetMapping("viaggi/search")
    public ResponseEntity<List<Viaggio>> searchViaggi(
        @RequestParam(required = false) String stato, 
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPartenza,
        @RequestParam(required = false) String tipologia) {

        List<Viaggio> viaggi;

        // Controlla se almeno uno dei parametri è stato fornito
        if (stato == null && dataPartenza == null && tipologia == null) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request se non ci sono parametri
        }

        // Se stato è fornito, verifica che esista nei paesi
        if (stato != null) {
            List<Paese> paesi = paeseServices.findAllPaesi();
            boolean statoTrovato = paesi.stream().anyMatch(paese -> paese.getStato().equalsIgnoreCase(stato));
            
            if (!statoTrovato) {
                return ResponseEntity.notFound().build(); // 404 se lo stato non è valido
            }
        }

        // Filtra i viaggi in base ai parametri forniti
        if (stato != null && tipologia != null && dataPartenza != null) {
            viaggi = viaggioServices.getViaggiByStatoTipologiaAndDataPartenza(stato, tipologia, dataPartenza);
        } else if (stato != null && tipologia != null) {
            viaggi = viaggioServices.getViaggiByStatoAndTipologia(stato, tipologia);
        } else if (stato != null && dataPartenza != null) {
            viaggi = viaggioServices.getViaggioByStatoAndDataPartenza(stato, dataPartenza);
        } else if (tipologia != null && dataPartenza != null) {
            viaggi = viaggioServices.getViaggiByTipologiaAndDataPartenza(tipologia, dataPartenza);
        } else if (stato != null) {
            viaggi = viaggioServices.getViaggioByStato(stato);
        } else if (tipologia != null) {
            viaggi = viaggioServices.getViaggiByTipologia(tipologia);
        } else {
            viaggi = viaggioServices.getViaggiByDataPartenza(dataPartenza);
        }

        // Restituisce la risposta in base ai risultati trovati
        if (!viaggi.isEmpty()) {
            return ResponseEntity.ok(viaggi);  // 200 OK, restituisce i viaggi trovati
        } else {
            return ResponseEntity.noContent().build();  // 204 No Content, nessun viaggio trovato
        }
    }


    // Creazione di un nuovo viaggio
    @PostMapping("viaggi")
    public ResponseEntity<Viaggio> addNewViaggio(@RequestBody Viaggio nuovoViaggio) {
        try {
            // 1. Recupera l'utente autenticato
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // 2. Trova l'utente dal database (presuppone che ci sia un metodo per cercare l'utente per username)
            Utente utente = utenteServices.findByUsername(username);
            
            List<Prenotazione> prenotazioniUtente =  prenoServices.findByUtente(utente);
            
            for (Prenotazione prenotazione : prenotazioniUtente) {
                LocalDate dataInizioEsistente = prenotazione.getData_Partenza();
                LocalDate dataFineEsistente = prenotazione.getData_Arrivo();
                LocalDate dataInizioNuovo = nuovoViaggio.getData_Partenza();
                LocalDate dataFineNuovo = nuovoViaggio.getData_Arrivo();

                // Verifica la sovrapposizione delle date
                if ((dataInizioNuovo.isBefore(dataFineEsistente) && dataFineNuovo.isAfter(dataInizioEsistente))
                        || dataInizioNuovo.equals(dataInizioEsistente) || dataFineNuovo.equals(dataFineEsistente)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(null); // 409 CONFLICT - Viaggio sovrapposto
                }
            }

            if (utente != null) {
                // 3. Assegna l'utente al viaggio
                nuovoViaggio.setUtente(utente);
                
                // 4. Salva il viaggio con l'utente assegnato
                Viaggio salvaViaggio = viaggioServices.createViaggio(nuovoViaggio);
                
                prenoServices.addPrenotazione(utente, salvaViaggio);
                return ResponseEntity.status(201).body(salvaViaggio);
            } else {
                return ResponseEntity.badRequest().body(null); // Restituisce 400 se l'utente non è trovato
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Gestisce eventuali errori
        }
    }

    
    @GetMapping("viaggi/partecipanti/{idViaggio}")
    public ResponseEntity<List<Utente>> getPartecipanti(@PathVariable int idViaggio) {
    	if(viaggioServices.findById(idViaggio) == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    	List<Prenotazione> prenotazioni = prenoServices.findAll();
    	List<Utente> Utenti = new ArrayList<>();
    	for (Prenotazione prenotazione : prenotazioni) {
			if(prenotazione.getViaggio().getId_viaggio() == idViaggio) {
				Utenti.add(prenotazione.getUtente());
			}
		}
    	return ResponseEntity.ok(Utenti);
    }
    
    @GetMapping("viaggi/nominativo/{username}")
    public ResponseEntity<List<Viaggio>> getViaggiByUsernameUser(@PathVariable String username) {
    	List<Viaggio> viaggio = viaggioServices.getViaggiByUsernameUser(username);
    		if (viaggio != null && !viaggio.isEmpty()) {
    	        return ResponseEntity.ok(viaggio);
    	    } else {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	    }
    }
    @PostMapping("viaggi/delete")
    public ResponseEntity<Void> deleteViaggio(@RequestBody Viaggio viaggio) {
        try {
            if (viaggioServices.findById(viaggio.getId_viaggio()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            List<Prenotazione> prenotazioniDaCancellare = prenoServices.findByViaggio(viaggio);
            
            viaggioServices.deleteViaggio(viaggio);
            
            for (Prenotazione prenotazione : prenotazioniDaCancellare) {
                if (prenotazione.getUtente() != null && prenotazione.getViaggio() != null) {
                    PrenotazioneId id = new PrenotazioneId();
                    id.setUtenteId(prenotazione.getUtente().getId_utente());
                    id.setViaggioId(prenotazione.getViaggio().getId_viaggio());
                    
                    Optional<Prenotazione> prenotazioneF = prenoServices.findById(id);
                    if (prenotazioneF.isPresent()) {
                        prenoServices.deletePrenotazione(prenotazione);
                    }
                }
            }
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } // Nessun contenuto da restituire
    }
    
    @GetMapping("viaggi/in-partenza")
    public ResponseEntity<List<Viaggio>> getViaggiInPartenza(){
    	List<Viaggio> viaggi = viaggioServices.getViaggiInPartenza();
    	
    	if (!viaggi.isEmpty()) {
            return ResponseEntity.ok(viaggi); // restituisce 200 e la lista dei 3 viaggi
        } else {
            return ResponseEntity.noContent().build(); // restituisce 204 se la lista è vuota
        }
    }
    
    @GetMapping("prenotazioni/viaggi/limite/{id}")
    public Boolean getLimite(@PathVariable int id){
    	
        Viaggio viaggio = viaggioServices.findById(id);

        if (viaggio != null && viaggio.getUtente() != null) {
        	int limit = viaggio.getPrenotazioni().size();
            return limit <= viaggio.getMaxPartecipanti();
        } else {
            return false; 
        }
    }
}
