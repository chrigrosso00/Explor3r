package com.explorer.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.explorer.entities.Preferiti;
import com.explorer.entities.Prenotazione;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.services.PaeseServices;
import com.explorer.services.PrenotazioneServices;
import com.explorer.services.UtenteServices;
import com.explorer.services.UtenteServicesImpl;
import com.explorer.services.ViaggioServices;

@RestController
@RequestMapping("api")
public class ViaggioREST {

    @Autowired
    private ViaggioServices viaggioServices;
    
    @Autowired
    private PaeseServices paeseServices;
    
    @Autowired
    private UtenteServices utenteService;
    
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

    // Ricerca i viaggi in base al nome dello stato
    @GetMapping("viaggi/search")
    public ResponseEntity<List<Viaggio>> getViaggiByStato(@RequestParam String stato, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPartenza) {
        List<Paese> paesi = paeseServices.findAllPaesi();
        
        boolean statoTrovato = paesi.stream().anyMatch(paese -> paese.getStato().equalsIgnoreCase(stato));

        if (!statoTrovato) {
            return ResponseEntity.notFound().build();
        } else {
            List<Viaggio> viaggi;
            
            if (dataPartenza != null) {
                // Se è presente la data di partenza, filtriamo per stato e data di partenza
                viaggi = viaggioServices.getViaggioByStatoAndDataPartenza(stato, dataPartenza);
            } else {
                // Altrimenti filtriamo solo per stato
                viaggi = viaggioServices.getViaggioByStato(stato);
            }

            if (!viaggi.isEmpty()) {
                return ResponseEntity.ok(viaggi); // restituisce 200 e la lista di tutti i viaggi
            } else {
                return ResponseEntity.noContent().build(); // restituisce 204 se la lista è vuota
            }
        }
    }

    // Filtro per trovare i viaggi in base alla difficoltà
    @GetMapping("viaggi/tipologia")
    public ResponseEntity<List<Viaggio>> getViaggiByTipologia(@RequestParam String tipologia) {
        List<Viaggio> viaggi = viaggioServices.getViaggiByTipologia(tipologia);

        if (viaggi.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 se non ci sono viaggi
        } else {
            return ResponseEntity.ok(viaggi); // 200 ok
        }
    }

    // Creazione di un nuovo viaggio
    @PostMapping("viaggi")
    public ResponseEntity<Viaggio> addNewViaggio(@RequestBody Viaggio nuovoViaggio) {
        try {
            // Usare il metodo createViaggio del servizio
            Viaggio salvaViaggio = viaggioServices.createViaggio(nuovoViaggio);
            return ResponseEntity.status(201).body(salvaViaggio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Gestire eventuali errori
        }
    }
    
    @GetMapping("viaggi/partecipanti/{idViaggio}")
    public ResponseEntity<List<Utente>> getPartecipanti(@RequestParam int idViaggio) {
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
}
