package com.explorer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicesImpl implements EmailServices  {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String destinatario, String oggetto, String testo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinatario);
            message.setSubject(oggetto);
            message.setText(testo);
            mailSender.send(message);
        } catch (MailException e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
            throw new RuntimeException("Errore nell'invio dell'email", e);
        }
    }
}