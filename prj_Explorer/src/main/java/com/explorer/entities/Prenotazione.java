package com.explorer.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "df_PRENOTAZIONE")
public class Prenotazione {
    
	@ManyToOne
    @MapsId("id_utente")
    @JoinColumn(name = "id_utente")
    private Utente utente;
    @ManyToOne
    @MapsId("id_viaggio")
    @JoinColumn(name = "id_viaggio")
    private Viaggio viaggio;
	
    public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
	}
}