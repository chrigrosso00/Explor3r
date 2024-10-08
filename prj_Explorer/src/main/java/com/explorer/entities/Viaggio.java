package com.explorer.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ft_viaggio")
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_viaggio;
    
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
    
    @OneToMany(mappedBy = "viaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
	private Set<Prenotazione> prenotazioni = new HashSet<>();
    
    private Double prezzo;
    private LocalDate data_Partenza;
    private LocalDate data_Arrivo;
    private String continente;
    private String stato;
    private String descrizione;
    private String itinerario;
    private String difficolta;
    
    
	public int getId_viaggio() {
		return id_viaggio;
	}
	public void setId_viaggio(int id_viaggio) {
		this.id_viaggio = id_viaggio;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Set<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
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
	public String getContinente() {
		return continente;
	}
	public void setContinente(String continente) {
		this.continente = continente;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
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
	public String getDifficolta() {
		return difficolta;
	}
	public void setDifficolta(String difficolta) {
		this.difficolta = difficolta;
	}
 
    
}