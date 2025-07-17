package com.example.domains.entities;

import java.util.List;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Table(value = "users")
public class Contacto {
	@PrimaryKey
	private String username;
	private String firstname;
	private String lastname;
//	private String alias;
//	private List<String> email;
//	private boolean active;

}
