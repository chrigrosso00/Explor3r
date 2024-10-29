package com.explorer.services;

import java.util.List;
import java.util.Optional;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.entities.Paese;
import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.repos.PaeseDAO;
import com.explorer.repos.PrenotazioneDAO;
import com.explorer.repos.UtenteDAO;
import com.explorer.repos.ViaggioDAO;


@Service
public class PrenotazioneServicesImpl implements PrenotazioneServices {
	@Autowired
    private PrenotazioneDAO dao;
	
	@Autowired
    private UtenteDAO utenteDAO;
	
	@Autowired
    private ViaggioDAO viaggiodao;
	
	@Override
    public List<Prenotazione> findAll() {
        return dao.findAll();
    }
	@Override
	public List<Prenotazione> findByUtente( Utente utente){
    	return dao.findByUtente(utente);
	} 
    @Override
	public List<Prenotazione> findByViaggio(Viaggio viaggio){
    	return dao.findByViaggio(viaggio);
	} 
    @Override
	public Optional<Prenotazione> findById(PrenotazioneId id){
    	return dao.findById(id);
	} 
    @Override
    public List<Prenotazione> findByData(String data){
    	return dao.findByData(data);
	}
    @Override
	public int countByUtente(Utente utente) {
		return dao.countByUtente(utente);
	} 
    @Override
	public Prenotazione addPrenotazione(Utente utente, Viaggio viaggio) {
    	
    	Prenotazione prenotazione = new Prenotazione();
    	
    	prenotazione.setUtente(utente);
        Viaggio viaggiop = viaggiodao.findById(viaggio.getId_viaggio()).orElseThrow();
        prenotazione.setViaggio(viaggiop);
        prenotazione.setData(new Date(System.currentTimeMillis()));
        prenotazione.setUsername(utente.getUsername());
        prenotazione.setPrezzo(viaggio.getPrezzo());
        prenotazione.setData_Partenza(viaggio.getData_Partenza());
        prenotazione.setData_Arrivo(viaggio.getData_Arrivo());
        prenotazione.setDescrizione(viaggio.getDescrizione());
        prenotazione.setItinerario(viaggio.getItinerario());
        prenotazione.setTipologia(viaggio.getTipologia());
        prenotazione.setPaese(viaggio.getPaese().getStato());
        		
    	return dao.save(prenotazione);
	}
    
    @Override
    public void deletePrenotazione(Prenotazione prenotazione) {
        dao.delete(prenotazione);
    }
    
    @Override
    public List<Prenotazione> findByUsernameUser(String username){
    	return dao.findPrenotazioniByUsernameUser(username);
    }
	@Override
	public void cancellaPrenotazione(Prenotazione p) {
		dao.delete(p);
		
	}
}
