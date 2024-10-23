package com.explorer.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.entities.Paese;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.repos.PaeseDAO;
import com.explorer.repos.UtenteDAO;
import com.explorer.repos.ViaggioDAO;

@Service
public class ViaggioServicesImpl implements ViaggioServices {
    
    @Autowired
    private ViaggioDAO dao;
    
    @Autowired
    private UtenteDAO utenteDAO;
    
    @Autowired
    private PaeseDAO paeseDAO;

    @Override
    public List<Viaggio> findAll() {
        return dao.findAll();
    }

    @Override
    public Viaggio findById(int id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Viaggio save(Viaggio viaggio) {
        // Recupera l'utente dal database
        if (viaggio.getUtente() != null && viaggio.getUtente().getId_utente() > 0) {
            Utente utente = utenteDAO.findById(viaggio.getUtente().getId_utente()).orElse(null);
            viaggio.setUtente(utente);  // Imposta l'utente completo
        }

        // Recupera il paese dal database
        if (viaggio.getPaese() != null && viaggio.getPaese().getId_paese() > 0) {
            Paese paese = paeseDAO.findById(viaggio.getPaese().getId_paese()).orElse(null);
            viaggio.setPaese(paese);  // Imposta il paese completo
        }

        // Salva il viaggio
        return dao.save(viaggio);
    }

    // Nuovo metodo per creare un viaggio
    public Viaggio createViaggio(Viaggio viaggio) {
        return save(viaggio); // Riutilizza il metodo save per creare un viaggio
    }

    @Override
    public List<Viaggio> getViaggiByContinente(String continente) {
        return dao.findByContinente(continente);
    }

    @Override
    public List<Viaggio> getViaggioByStato(String stato) {
        return dao.findByStato(stato);
    }

    @Override
    public List<Viaggio> getViaggiByTipologia(String tipologia) {
        return dao.findByTipologia(tipologia);
    }

	@Override
	public List<Viaggio> getViaggioByStatoAndDataPartenza(String stato, LocalDate datPartenza) {
		return dao.findByStatoAndDataPartenza(stato, datPartenza);
	}
	
	public List<Viaggio> getViaggiByUsernameUser(String username){
		return dao.findViaggiByUsernameUser(username);
	}
}
