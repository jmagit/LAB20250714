package com.example.infraestructure.services;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.contracts.application.EMailService;

@Service
@Primary
public class EMailServiceMock implements EMailService {

	@Override
	@Async
	public void sendEmail(String to, String subject, String body) {
        System.out.println("🚀 [Hilo: %s] Iniciando envío de correo a: %s".formatted(Thread.currentThread().getName(), to));
        try {
            // Simular un proceso que tarda tiempo, como enviar un email real
            Thread.sleep(5000); // 5 segundos
            System.out.println("🚀 [Hilo: %s] Envío de correo a: %s asunto: %s".formatted(Thread.currentThread().getName(), to, subject));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ [Hilo: %s] El envío de correo fue interrumpido.".formatted(Thread.currentThread().getName()));
            return;
        }
        System.out.println("✅ [Hilo: %s] Correo de bienvenida enviado a %s (%s)".formatted(Thread.currentThread().getName(), to, subject));


	}

	@Override
	@Async
	public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
		// TODO Auto-generated method stub

	}

}
