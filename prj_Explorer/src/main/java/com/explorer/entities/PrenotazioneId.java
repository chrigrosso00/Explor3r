package com.explorer.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class PrenotazioneId implements Serializable {
	
    private int utente_id;

    private int viaggio_id;

	public int getUtenteId() {
		return utente_id;
	}

	public void setUtenteId(int utenteId) {
		this.utente_id = utenteId;
	}

	public int getViaggioId() {
		return viaggio_id;
	}

	public void setViaggioId(int viaggioId) {
		this.viaggio_id = viaggioId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(utente_id, viaggio_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrenotazioneId other = (PrenotazioneId) obj;
		return utente_id == other.utente_id && viaggio_id == other.viaggio_id;
	}
}
