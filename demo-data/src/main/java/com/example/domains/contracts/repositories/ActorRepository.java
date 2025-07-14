package com.example.domains.contracts.repositories;

import java.util.Date;
import java.util.List;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Actor;

public interface ActorRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
}
