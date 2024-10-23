package com.explorer.entities;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ft_prenotazione")
public class Prenotazione {
    @EmbeddedId
    private PrenotazioneId id = new PrenotazioneId();
	
	@ManyToOne
    @MapsId("utente_id")
	@JoinColumn(name = "utente_id")
	@JsonBackReference("utente-prenotazione")
    private Utente utente;
	
    @ManyToOne
    @MapsId("viaggio_id")
    @JoinColumn(name = "viaggio_id")
    @JsonBackReference("viaggio-prenotazione")
    private Viaggio viaggio;
    
    private Date data;
	
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
	public PrenotazioneId getId() {
		return id;
	}
	public void setId(PrenotazioneId id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}