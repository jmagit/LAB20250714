package com.example.contracts.application;

import org.springframework.scheduling.annotation.Async;

public interface EMailService {
	/**
	 * Envia un correo electrónico.
	 *
	 * @param to destinatario del correo
	 * @param subject asunto del correo
	 * @param body cuerpo del correo
	 */
	@Async
	void sendEmail(String to, String subject, String body);

	/**
	 * Envia un correo electrónico con un archivo adjunto.
	 *
	 * @param to destinatario del correo
	 * @param subject asunto del correo
	 * @param body cuerpo del correo
	 * @param attachmentPath ruta del archivo adjunto
	 */
	@Async
	void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath);
}
