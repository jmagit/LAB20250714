package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domains.entities.User;

// CREATE INDEX IF NOT EXISTS firstname_idx ON videodb.users (firstname);

@RepositoryRestResource(path="usuarios", itemResourceRel="usuario", collectionResourceRel="usuarios")
public interface UsersRepository extends CassandraRepository<User, String> {
	List<User> findByFirstname(String nombre);
	List<User> findByLastname(String apellidos);
}
