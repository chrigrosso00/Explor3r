package com.explorer.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class PrenotazioneId implements Serializable {
	
    private int utenteId;

    private int viaggioId;

	public int getUtenteId() {
		return utenteId;
	}

	public void setUtenteId(int utenteId) {
		this.utenteId = utenteId;
	}

	public int getViaggioId() {
		return viaggioId;
	}

	public void setViaggioId(int viaggioId) {
		this.viaggioId = viaggioId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(utenteId, viaggioId);
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
		return utenteId == other.utenteId && viaggioId == other.viaggioId;
	}
}
