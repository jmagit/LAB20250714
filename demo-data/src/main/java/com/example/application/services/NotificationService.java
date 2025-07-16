package com.example.application.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.contracts.application.EMailService;

@Service
public class NotificationService {
	private final EMailService srv;
	
	public NotificationService(EMailService srv) {
		this.srv = srv;
	}
	
	@Async
	public void sendWelcomeEmail(String to, String name) {
		String subject = "Bienvenido a nuestra aplicación";
		String body = "Hola " + name + ",\n\n¡Gracias por unirte a nosotros!";
		srv.sendEmail(to, subject, body);
	}

}
