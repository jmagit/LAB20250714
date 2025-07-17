package com.example.presentation.resources;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.application.services.NotificationService;
import com.example.contracts.domain.repositories.sakila.FilmRepository;
import com.example.core.domain.exceptions.NotFoundException;

import lombok.Value;

@RestController
@RequestMapping(path = "/")
public class DemosResource {
	@Autowired
	private NotificationService srv;

	@Value
	public static class AppResources {
		@Value
		public class RootLinks {
			public class Href {
				private String href;
				public String getHref() { return href; }
				public Href(String path) {
					href = ServletUriComponentsBuilder.fromCurrentRequest().path(path).toUriString();
				}
			}
			private Href self = new Href("");
			private Href saludo = new Href("/correo/v1");
			private Href documentacion = new Href("/open-api");
		}

		private RootLinks _links = new RootLinks();
	}

	@GetMapping(path = "/")
	public ResponseEntity<AppResources> getResources() {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/hal+json").body(new AppResources());
	}
	
	@GetMapping(path = "/correo/v1")
	public String enviar() {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo/v1".formatted(Thread.currentThread().getName()));
		srv.sendWelcomeEmail("usr@example.com", "Usuario de ejemplo");
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}
	
	@GetMapping(path = "/correo/v1/attachment")
	public String enviarWithAttachment(@RequestParam(defaultValue = "usr@example.com") String to, 
			@RequestParam(defaultValue = "Hola") String subject,
			@RequestParam String file) {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo/v1".formatted(Thread.currentThread().getName()));
		srv.sendEmail(to, subject, "Este es un mensaje de prueba enviado desde el servicio de correo.", file);
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}

	@Autowired
	FilmRepository daoFilm;
	
	@GetMapping(path = "/correo/v1/{id}/query")
	public String enviar(@PathVariable int id, @RequestParam(defaultValue = "usr@example.com") String to) throws NotFoundException {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo/v1".formatted(Thread.currentThread().getName()));
		var item = daoFilm.findById(id).orElseThrow(() -> new NotFoundException("No se encontró la película con ID: " + id));
		srv.sendEmail(to, item.getTitle(), "Detalles de la película: " + item.getDescription());
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}
	
	@GetMapping(path = "/correo/v1/{id}/mime")
	public String enviarMime(@PathVariable int id, @RequestParam(defaultValue = "usr@example.com") String to) throws NotFoundException {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo/v1".formatted(Thread.currentThread().getName()));
		var item = daoFilm.findById(id).orElseThrow(() -> new NotFoundException("No se encontró la película con ID: " + id));
		var body = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Película</title>
</head>
<body>
    <h1>%s</h1>
    <p>%s</p>
</body>
</html>
""".formatted(item.getTitle(), item.getDescription());
		srv.sendMimeEmail(to, item.getTitle(), body, true);
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}

}
