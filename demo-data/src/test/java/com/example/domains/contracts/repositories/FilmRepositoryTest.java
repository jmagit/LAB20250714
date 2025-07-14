package com.example.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;

import jakarta.transaction.Transactional;

@SpringBootTest
class FilmRepositoryTest {
	@Autowired
	FilmRepository dao;
	
	@Autowired
	ActorRepository daoActor;
	
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
//		source.addCategory(1);
		var actual = dao.save(source);
		assertNotNull(actual);
	}

}
