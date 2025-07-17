package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.contracts.domain.repositories.ContactoRepository;
import com.example.contracts.domain.repositories.UsersRepository;
import com.example.domains.entities.Contacto;

@SpringBootApplication
public class CassandraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CassandraApplication.class, args);
	}

//    @Bean 
//    CqlSession session() {
//		return CqlSession.builder().withKeyspace("videodb").build();
//	}
 
	@Autowired
	UsersRepository dao;
	@Autowired
	ContactoRepository dao2;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada");
		
		dao.findAll().forEach(System.out::println);
//		dao2.save(new Contacto("contacto1", "pepito","grillo" ));
		dao2.findAll().forEach(System.out::println);
	}

}
