package com.explorer.entities;

import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private Double prezzo;
    private String username;
    private LocalDate data_Partenza;
    private LocalDate data_Arrivo;
    private String descrizione;
    private String itinerario;
    private String tipologia;
    
    
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public LocalDate getData_Partenza() {
		return data_Partenza;
	}
	public void setData_Partenza(LocalDate data_Partenza) {
		this.data_Partenza = data_Partenza;
	}
	public LocalDate getData_Arrivo() {
		return data_Arrivo;
	}
	public void setData_Arrivo(LocalDate data_Arrivo) {
		this.data_Arrivo = data_Arrivo;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getItinerario() {
		return itinerario;
	}
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
	}
	
}