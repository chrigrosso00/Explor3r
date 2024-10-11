package com.explorer.controllers;

public class PrenotazioneRequest {
	
	private int viaggioId;
	private String data;
	
	public int getViaggioId() {
		return viaggioId;
	}
	public void setViaggioId(int viaggioId) {
		this.viaggioId = viaggioId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
