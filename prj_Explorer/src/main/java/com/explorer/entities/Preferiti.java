package com.explorer.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "df_preferiti")
public class Preferiti {

    @EmbeddedId
    private PreferitiId id;

    @ManyToOne
    @MapsId("id_utente")
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne
    @MapsId("id_viaggio")
    @JoinColumn(name = "id_viaggio")
    private Viaggio viaggio;

    // Getters e Setters
    public PreferitiId getId() {
        return id;
    }

    public void setId(PreferitiId id) {
        this.id = id;
    }

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

	
	
	
	
	

		

