package com.explorer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	 public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to); 
		message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
	 }
	
	// Metodo per inviare l'email di benvenuto
	public void sendWelcomeEmail(String userEmail, String userName) {
        String subject = "Benvenuto nel nostro sito di viaggi!";
        String body = "Ciao " + userName + ",\n\n"
                + "Benvenuto nel nostro sito di viaggi! Siamo entusiasti di averti con noi "
                + "e non vediamo l'ora di aiutarti a pianificare il tuo prossimo viaggio.\n\n"
                + "Cordiali saluti,\nIl team di Viaggi";

        sendEmail(userEmail, subject, body);
	}
}
