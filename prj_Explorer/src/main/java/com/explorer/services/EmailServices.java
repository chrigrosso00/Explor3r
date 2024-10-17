package com.explorer.services;

public interface EmailServices {
	void sendEmail(String destinatario, String oggetto, String testo);
}
