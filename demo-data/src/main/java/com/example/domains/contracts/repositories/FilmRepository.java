package com.example.domains.contracts.repositories;

import java.util.Date;
import java.util.List;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
}
