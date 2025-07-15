package com.example.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.contracts.domain.repositories.sakila.ActorRepository;
import com.example.contracts.domain.repositories.sakila.CategoryRepository;
import com.example.contracts.domain.repositories.sakila.FilmRepository;
import com.example.domain.entities.sakila.Film;
import com.example.domain.entities.sakila.Language;
import com.example.domain.entities.sakila.Film.Rating;

import jakarta.transaction.Transactional;

@SpringBootTest
class FilmRepositoryTest {
	@Autowired
	FilmRepository dao;
	
	@Autowired
	ActorRepository daoActor;
	
	@Autowired
	CategoryRepository daoCategory;
	
	@Test
	@Disabled
	void testFindAll() {
		var actual = dao.count();
		assertThat(actual).isGreaterThanOrEqualTo(1000);
	}
	
	@Test
	@Transactional
	@Disabled
	void testFindById() {
		var actual = dao.findById(1);
		assertNotNull(actual);
		assertTrue(actual.isPresent());
		assertThat(actual.get().getActors().size()).isGreaterThanOrEqualTo(10);
	}

	
	@Test
	@Transactional
	void create() {
		Film source = new Film(0, "uno", "uno", (short) 2001, new Language(1), new Language(1), (byte) 1,
				new BigDecimal("1.0"), 1, new BigDecimal("1.0"), Rating.GENERAL_AUDIENCES);
		source.addActor(daoActor.findById(1).get());
		source.addActor(daoActor.findById(2).get());
		source.addCategory(daoCategory.findById(2).get());
		var actual = dao.save(source);
		assertNotNull(actual);
	}

}
