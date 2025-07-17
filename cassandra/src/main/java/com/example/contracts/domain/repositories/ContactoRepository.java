package com.example.contracts.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domains.entities.Contacto;

@RepositoryRestResource(path="contactos", itemResourceRel="contacto", collectionResourceRel="contactos")
public interface ContactoRepository extends CrudRepository<Contacto, String> {

}
