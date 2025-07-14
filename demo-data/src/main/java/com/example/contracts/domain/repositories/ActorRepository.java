package com.example.contracts.domain.repositories;

import java.util.Date;
import java.util.List;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domain.entities.Actor;

public interface ActorRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
}
