package com.example.contracts.domain.repositories;

import java.util.Date;
import java.util.List;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domain.entities.Category;

public interface CategoryRepository extends ProjectionsAndSpecificationJpaRepository<Category, Integer> {
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
}
