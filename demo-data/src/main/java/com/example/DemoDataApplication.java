package com.example;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}

//	@Bean
//	CommandLineRunner demo(FilmRepository dao, ActorRepository daoActor) {
//		return  args -> {
//			Film source = new Film(0, "uno", "uno", (short) 2001, new Language(1), new Language(1), (byte) 1,
//					new BigDecimal("1.0"), 1, new BigDecimal("1.0"), Rating.GENERAL_AUDIENCES);
//			source.addActor(daoActor.findById(1).get());
//			source.addActor(daoActor.findById(2).get());
//	//		source.addCategory(1);
//			var actual = dao.save(source);
//			System.err.println(actual);
//		};
//	}

	@Autowired
	FilmRepository dao;
	
	@Autowired
	ActorRepository daoActor;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Film source = new Film(0, "uno", "uno", (short) 2001, new Language(1), new Language(1), (byte) 1,
				new BigDecimal("1.0"), 1, new BigDecimal("1.0"), Rating.GENERAL_AUDIENCES);
		source.addActor(daoActor.findById(1).get());
		source.addActor(daoActor.findById(2).get());
		// source.addCategory(1);
		var actual = dao.save(source);
		System.err.println(actual);
	}
}
