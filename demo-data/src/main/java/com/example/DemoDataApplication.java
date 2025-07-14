package com.example;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.contracts.domain.repositories.ActorRepository;
import com.example.contracts.domain.repositories.CategoryRepository;
import com.example.contracts.domain.repositories.FilmRepository;
import com.example.domain.entities.Film;
import com.example.domain.entities.Language;
import com.example.domain.entities.models.ActorEdit;
import com.example.domain.entities.Film.Rating;

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
	FilmRepository daoFilm;
	
	@Autowired
	ActorRepository daoActor;
	
	@Autowired
	CategoryRepository daoCategory;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Inicia");
//		Film source = new Film(0, "uno", "uno", (short) 2001, new Language(1), new Language(1), (byte) 1,
//				new BigDecimal("1.0"), 1, new BigDecimal("1.0"), Rating.GENERAL_AUDIENCES);
//		source.addActor(daoActor.findById(1).get());
//		source.addActor(daoActor.findById(2).get());
//		source.addCategory(2);
//		source.addCategory(daoCategory.findById(2).get());
//		Film source = new Film(1002, "uno", "uno", (short) 2001, new Language(1), new Language(1), (byte) 1,
//				new BigDecimal("1.0"), 1, new BigDecimal("1.0"), Rating.GENERAL_AUDIENCES);
//		source.addActor(daoActor.findById(3).get());
//		source.addActor(daoActor.findById(2).get());
//		source.addCategory(daoCategory.findById(1).get());
//		var actual = daoFilm.save(source);
//		System.err.println(actual);
//		daoActor.findAllBy(ActorEdit.class).forEach(System.out::println);
	}
}
