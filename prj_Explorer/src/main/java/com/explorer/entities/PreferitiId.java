package com.explorer.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PreferitiId implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_utente;
    private int id_viaggio;

    // Costruttore vuoto
    public PreferitiId() {}

    // Costruttore con parametri
    public PreferitiId(int id_utente, int id_viaggio) {
        this.id_utente = id_utente;
        this.id_viaggio = id_viaggio;
    }

    // Getters e Setters
    public int getIdUtente() {
        return id_utente;
    }

    public void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getIdViaggio() {
        return id_viaggio;
    }

    public void setIdViaggio(int id_viaggio) {
        this.id_viaggio = id_viaggio;
    }

    // Implementazione di equals e hashCode per il corretto funzionamento della chiave composta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferitiId that = (PreferitiId) o;
        return id_utente == that.id_utente && id_viaggio == that.id_viaggio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_utente, id_viaggio);
    }
}
