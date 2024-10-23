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
    private String stato;
    private String continente;
    private String img;
    
	
	public int getId_paese() {
		return id_paese;
	}
	public void setId_paese(int id_paese) {
		this.id_paese = id_paese;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		stato = stato;
	}
	public String getContinente() {
		return continente;
	}
	public void setContinente(String continente) {
		continente = continente;
	} 
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
}