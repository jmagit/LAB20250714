package com.example.contracts.domain.repositories.cursos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.cursos.Contacto;

public interface ContactosRepository extends JpaRepository<Contacto, Integer> {

}
