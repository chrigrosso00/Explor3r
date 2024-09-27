package com.explorer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "df_PRENOTAZIONE")
public class Prenotazione {
    
	@EmbeddedId
    private PrenotazioneId id = new PrenotazioneId();
	
	@ManyToOne
    @MapsId("utenteId")
    @JoinColumn(name = "id_utente")
	@JsonManagedReference
    private Utente utente;
	
    @ManyToOne
    @MapsId("viaggioId")
    @JoinColumn(name = "id_viaggio")
    @JsonBackReference
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