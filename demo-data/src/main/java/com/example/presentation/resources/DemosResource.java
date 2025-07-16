package com.example.presentation.resources;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.application.services.NotificationService;

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

}
