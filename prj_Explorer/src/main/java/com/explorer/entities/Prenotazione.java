package com.explorer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @MapsId("utenteId")
	@JoinColumn(name = "id_utente")
	@JsonBackReference("utente-prenotazione")
    private Utente utente;
	
    @ManyToOne
    @MapsId("viaggioId")
    @JoinColumn(name = "id_viaggio")
    @JsonBackReference("viaggio-prenotazione")
    private Viaggio viaggio;
    
    @NotNull
    @Column(nullable = false)
    private String data;
	
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}