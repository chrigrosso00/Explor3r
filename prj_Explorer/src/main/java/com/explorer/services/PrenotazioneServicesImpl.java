package com.explorer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.entities.Prenotazione;
import com.explorer.entities.PrenotazioneId;
import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.repos.PrenotazioneDAO;


@Service
public class PrenotazioneServicesImpl implements PrenotazioneServices {
	@Autowired
    private PrenotazioneDAO dao;
	
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
	public Prenotazione addPrenotazione(Prenotazione prenotazione) {
    	return dao.save(prenotazione);
	}
    @Override
    public void deletePrenotazione(int utenteId, int viaggioId) {
        PrenotazioneId id = new PrenotazioneId();
        id.setUtenteId(utenteId);
        id.setViaggioId(viaggioId);
        dao.deleteById(id);
    }

}
