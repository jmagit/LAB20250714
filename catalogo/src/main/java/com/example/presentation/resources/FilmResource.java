package com.example.presentation.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
//import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.contracts.domain.services.FilmService;
import com.example.core.domain.exceptions.BadRequestException;
import com.example.core.domain.exceptions.NotFoundException;
import com.example.domain.entities.Category;
import com.example.domain.entities.Film;
import com.example.domain.entities.Film.Rating;
import com.example.domain.entities.models.ActorDTO;
import com.example.domain.entities.models.FilmDetails;
import com.example.domain.entities.models.FilmEdit;
import com.example.domain.entities.models.FilmShort;
import com.example.infraestructure.proxies.MeGustaProxy;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@Tag(name = "peliculas-service", description = "Mantenimiento de peliculas")
@RequestMapping(path = "/peliculas/v1")
public class FilmResource {
	@Autowired
	private FilmService srv;

	@Hidden
	@GetMapping(params = "page")
	public PagedModel<FilmShort> getAll(Pageable pageable, @RequestParam(defaultValue = "short") String mode) {
		return new PagedModel<FilmShort>(srv.getByProjection(pageable, FilmShort.class));
	}

	@Operation(summary = "Listado de las peliculas", 
			description = "Recupera la lista de peliculas en formato corto o detallado, se puede paginar.",
			parameters = {
					@Parameter(in = ParameterIn.QUERY, name = "mode", required = false, description = "Formato de las peliculas", 
								schema = @Schema(type = "string", allowableValues = {"details", "short" }, defaultValue = "short")) }, 
			responses = {
					@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
							schema = @Schema(anyOf = {
									FilmShort.class, FilmDetails.class }))) })
	@GetMapping(params = { "page", "mode=details" })
	@PageableAsQueryParam
	@Transactional
	public PagedModel<FilmDetails> getAllDetailsPage(@Parameter(hidden = true) Pageable pageable,
			@RequestParam(defaultValue = "short") String mode) {
		var content = srv.getAll(pageable);
		return new PagedModel<>(new PageImpl<>(content.getContent().stream().map(item -> FilmDetails.from(item)).toList(), pageable,
				content.getTotalElements()));
	}

	@Hidden
	@GetMapping
	public List<FilmShort> getAll(@RequestParam(defaultValue = "short") String mode) {
		return srv.getByProjection(FilmShort.class);
	}

	@Hidden
	@GetMapping(params = "mode=details")
	public List<FilmDetails> getAllDetails(
//			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		return srv.getAll().stream().map(item -> FilmDetails.from(item)).toList();
	}

	record Search(
			@Schema(description = "Que el titulo contenga")
			String title, 
			@Schema(description = "Duración mínima de la pelicula")
			Integer minlength, 
			@Schema(description = "Duración máxima de la pelicula")
			Integer maxlength, 
			@Schema(description = "La clasificación por edades asignada a la película", allowableValues = {"G", "PG", "PG-13", "R", "NC-17"})
			@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
			String rating,
			@Schema(description = "Formato de la respuesta", type = "string", allowableValues = {
					"details", "short" }, defaultValue = "short")
			String mode
			) {}

	@Operation(summary = "Consulta filtrada de peliculas")
	@GetMapping("/filtro")
	public List<?> search(@ParameterObject @Valid Search filter) throws BadRequestException {
		if(filter.minlength != null && filter.maxlength != null && filter.minlength > filter.maxlength)
				throw new BadRequestException("la duración máxima debe ser superior a la mínima");
		Specification<Film> spec = null;
		if(filter.title != null && !"".equals(filter.title)) {
			Specification<Film> cond = (root, query, builder) -> builder.like(root.get("title"), "%" + filter.title.toUpperCase() + "%");
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.rating != null && !"".equals(filter.rating)) {
			if(!List.of(Rating.VALUES).contains(filter.rating))
				throw new BadRequestException("rating desconocido");
			Specification<Film> cond = (root, query, builder) -> builder.equal(root.get("rating"), Rating.getEnum(filter.rating));
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.minlength != null) {
			Specification<Film> cond = (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("length"), filter.minlength);
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.maxlength != null) {
			Specification<Film> cond = (root, query, builder) -> builder.lessThanOrEqualTo(root.get("length"), filter.maxlength);
			spec = spec == null ? cond : spec.and(cond);
		}
		if(spec == null)
			throw new BadRequestException("Faltan los parametros de filtrado");
		var query = srv.getAll(spec).stream();
		if("short".equals(filter.mode))
			return query.map(e -> FilmShort.from(e)).toList();
		else {
			return query.map(e -> FilmDetails.from(e)).toList();
		}
	}

	@GetMapping(path = "/{id}", params = "mode=short")
	public FilmShort getOneCorto(
			@Parameter(description = "Identificador de la pelicula", required = true) 
			@PathVariable 
			int id,
			@Parameter(required = false, allowEmptyValue = true, schema = @Schema(type = "string", allowableValues = {
					"details", "short", "edit" }, defaultValue = "edit")) 
			@RequestParam(required = false, defaultValue = "edit") 
			String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmShort.from(rslt.get());
	}

	@GetMapping(path = "/{id}", params = "mode=details")
	public FilmDetails getOneDetalle(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmDetails.from(rslt.get());
	}

	@Operation(summary = "Recupera una pelicula", description = "Están disponibles una versión corta, una versión con los detalles donde se han transformado las dependencias en cadenas y una versión editable donde se han transformado las dependencias en sus identificadores.")
	@GetMapping(path = "/{id}")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada", content = @Content(schema = @Schema(oneOf = {
			FilmShort.class, FilmDetails.class, FilmEdit.class })))
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
	public FilmEdit getOneEditar(
			@Parameter(description = "Identificador de la pelicula", required = true) 
			@PathVariable 
			int id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) 
			@RequestParam(required = false, defaultValue = "edit") 
			String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmEdit.from(rslt.get());
	}

	@Operation(summary = "Listado de los actores de la pelicula")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
	}

	@Operation(summary = "Listado de las categorias de la pelicula")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getCategories();
	}

	@GetMapping(path = "/clasificaciones")
	@Operation(summary = "Listado de las clasificaciones por edades")
	public List<Map<String, String>> getClasificaciones() {
		return List.of(Map.of("key", "G", "value", "Todos los públicos"),
				Map.of("key", "PG", "value", "Guía paternal sugerida"),
				Map.of("key", "PG-13", "value", "Guía paternal estricta"), 
				Map.of("key", "R", "value", "Restringido"),
				Map.of("key", "NC-17", "value", "Prohibido para audiencia de 17 años y menos"));
	}

	@GetMapping(path = "/contenido-adicional")
	@Operation(summary = "Listado del contenido adicional posible")
	public List<String> getSpecialFeatures() {
		return List.of(Film.SpecialFeature.values()).stream().map(o -> o.getValue()).toList();
	}

	@Operation(summary = "Añadir una nueva pelicula")
	@ApiResponse(responseCode = "201", description = "Pelicula añadida")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(@RequestBody() FilmEdit item) throws Exception {
		Film newItem = srv.add(FilmEdit.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Modificar una pelicula existente", description = "Los identificadores deben coincidir")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@SecurityRequirement(name = "bearerAuth")
//	@Transactional
	@PutMapping(path = "/{id}")
	public FilmEdit modify(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Valid @RequestBody FilmEdit item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		return FilmEdit.from(srv.modify(FilmEdit.from(item)));
	}

	@Operation(summary = "Borrar una pelicula existente")
	@ApiResponse(responseCode = "204", description = "Pelicula borrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		srv.deleteById(id);
	}

	@Autowired
	MeGustaProxy proxy;

//	@Operation(summary = "Enviar un me gusta")
//	@ApiResponse(responseCode = "200", description = "Like enviado")
//	@PostMapping(path = "{id}/like")
//	public String like(@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
//			throws Exception {
//		return proxy.sendLike(id);
//	}

//	@PreAuthorize("hasRole('ADMINISTRADORES')")
	@Operation(summary = "Enviar un me gusta")
	@ApiResponse(responseCode = "200", description = "Like enviado")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(path = "{id}/like")
	public String like(@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(hidden = true) @RequestHeader(required = false) String authorization) throws Exception {
		try {
			if (authorization == null)
				return proxy.sendLike(id);
			return proxy.sendLike(id, authorization);
		} catch(FeignException ex) {
			throw new ResponseStatusException(ex.status() < 0 ? 500 : ex.status(), ex.getMessage(), ex);
		}
	}
}
