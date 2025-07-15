package com.example;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.example.contracts.domain.repositories.cursos.ContactosRepository;
import com.example.contracts.domain.repositories.sakila.ActorRepository;
import com.example.contracts.domain.repositories.sakila.CategoryRepository;
import com.example.contracts.domain.repositories.sakila.FilmRepository;
import com.example.domain.entities.sakila.Film;
import com.example.domain.entities.sakila.Language;
import com.example.domain.entities.sakila.Film.Rating;
import com.example.domain.entities.sakila.models.ActorEdit;

import jakarta.transaction.Transactional;

@SpringBootApplication()
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}


	@Bean
	CommandLineRunner demo(ActorRepository daoActor, ContactosRepository daoContactos) {
		return  args -> {
			System.err.println(daoActor.findById(1));
			System.err.println(daoContactos.findById(1));
		};
	}

//	@Bean
//	CommandLineRunner sakila(ActorRepository daoActor) {
//		return  args -> {
//			daoActor.findAllBy(ActorEdit.class).forEach(System.out::println);
//		};
//	}
//
//	@Bean
//	CommandLineRunner cursos(ContactosRepository daoContactos) {
//		return  args -> {
//			daoContactos.findAll().forEach(System.out::println);
//		};
//	}

    
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

//	@Autowired
//	FilmRepository daoFilm;
//	
//	@Autowired
//	ActorRepository daoActor;
//	
//	@Autowired
//	CategoryRepository daoCategory;
	
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
