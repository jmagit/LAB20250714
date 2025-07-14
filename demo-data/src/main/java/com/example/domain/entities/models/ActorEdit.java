package com.example.domain.entities.models;

import org.springframework.data.annotation.PersistenceCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActorEdit {
	private int id;
	private String nombre;
	private String apellidos;

	@PersistenceCreator
	public ActorEdit(int actorId, String firstName, String lastName) {
		super();
		this.id = actorId;
		this.nombre = firstName;
		this.apellidos = lastName;
	}

	
}
