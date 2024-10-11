package com.explorer.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ft_paese")
public class Paese {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_paese;
    private String Stato;
    private String Continente;
    
	public int getId_paese() {
		return id_paese;
	}
	public void setId_paese(int id_paese) {
		this.id_paese = id_paese;
	}
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