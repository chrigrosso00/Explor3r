package com.explorer.services;

import java.time.LocalDate;
import java.util.List;

import com.explorer.entities.Viaggio;

public interface ViaggioServices {
	List<Viaggio> findAll();
	
	Viaggio findById(int id);
	
	Viaggio save(Viaggio viaggio);
	
	public List<Viaggio> getViaggiByContinente(String continente);
	
	public List<Viaggio> getViaggioByStato(String stato);
	
	public List<Viaggio> getViaggiByTipologia(String tipologia);

	Viaggio createViaggio(Viaggio nuovoViaggio);
	
	public List<Viaggio> getViaggioByStatoAndDataPartenza(String stato, LocalDate datPartenza);
	
	public List<Viaggio> getViaggiByUsernameUser(String username);
	
    public List<Viaggio> getViaggiByStatoAndTipologia(String stato, String tipologia);
    
    public List<Viaggio> getViaggiByTipologiaAndDataPartenza(String tipologia, LocalDate dataPartenza);
    
    public List<Viaggio> getViaggiByStatoTipologiaAndDataPartenza(String stato, String tipologia, LocalDate dataPartenza);
    
    public List<Viaggio> getViaggiByDataPartenza(LocalDate dataPartenza);
    
    public void deleteViaggio(Viaggio viaggio);
}
