package com.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeMap;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;

import com.example.contracts.domain.repositories.ContactoRepository;
import com.example.domains.entities.Contacto;
import com.example.domains.entities.Direccion;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Validator;

@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: Contactos",
                version = "1.0",
                description = "Ejemplo de Microservicio utilizando la base de datos **mongodb**.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20220725")
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SpringBootApplication
//@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class ContactosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContactosApplication.class, args);
	}

    @Bean
    OpenApiCustomizer sortSchemasAlphabetically() {
        return openApi -> {
            var schemas = openApi.getComponents().getSchemas();
            openApi.getComponents().setSchemas(new TreeMap<>(schemas));
        };
    }

//    @Bean
//    ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean factory) {
//        return new ValidatingMongoEventListener(factory);
//    }
    @Bean
    ValidatingEntityCallback validatingEntityCallback(Validator validator) {
      return new ValidatingEntityCallback(validator);
    }

//	@Autowired
//	ContactoRepository dao;

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Instancia arrancada");
	}

	@Bean
	CommandLineRunner init(ContactoRepository dao) {
		return args -> {
			Contacto contacto;
//			int id = 1;
//			Optional<Contacto> encontrado = dao.findAll(PageRequest.of(0, 1, Sort.by(Direction.DESC, "id"))).stream()
//					.findFirst();
//			if (encontrado.isPresent())
//				id = encontrado.get().getId() + 1;
			//
//			contacto = new Contacto(null, "Sr.", "Pepito", "Grillo", List.of("555666777"), "pepito@grillo", "M",
//					LocalDate.now().minus(20, ChronoUnit.YEARS),
//					"https://upload.wikimedia.org/wikipedia/commons/b/b5/Jiminy_Cricket.png", true, null);
//			contacto.getDirecciones().add(new Direccion("  ", "28080", "Madrid", "España"));
////			contacto.getDirecciones().add(new Direccion("Calle Falsa 123", "28080", "Madrid", "España"));
//			if(contacto.isInvalid())
//				contacto.getErrors().forEach(System.err::println);
//			else
//				dao.save(contacto);
//			System.out.println("Creado");
//			encontrado = dao.findById(id);
//			if (encontrado.isPresent()) {
//				System.out.println(encontrado.get());
//			} else
//				System.out.println("No encontrado");
//			if (encontrado.isPresent()) {
//				encontrado.get().setNombre(encontrado.get().getNombre().toUpperCase());
//				encontrado.get().setApellidos(encontrado.get().getApellidos().toUpperCase());
//				dao.save(encontrado.get());
//				System.out.println("Modificado");
//			}
//			encontrado = dao.findById(id);
//			if (encontrado.isPresent()) {
//				System.out.println(encontrado.get());
//			} else
//				System.out.println("No encontrado");
//			dao.deleteById(id);
//			System.out.println("Borrado");
//			encontrado = dao.findById(id);
//			System.out.println(encontrado.isPresent() ? encontrado.get() : "No encontrado");
//			dao.findByConflictivoTrue().forEach(System.out::println);
		};
	}
}
