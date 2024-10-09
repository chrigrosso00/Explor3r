package com.explorer.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ft_paese")
public class Paese {
    @Id
    private String Stato;
    private String Continente;
	
    public String getStato() {
		return Stato;
	}
	public void setStato(String stato) {
		Stato = stato;
	}
	public String getContinente() {
		return Continente;
	}
	public void setContinente(String continente) {
		Continente = continente;
	}
}