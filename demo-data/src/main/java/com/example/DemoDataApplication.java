package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

import com.example.contracts.domain.repositories.cursos.ContactosRepository;
import com.example.contracts.domain.repositories.sakila.ActorRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}


//	@Bean	
//	CommandLineRunner demo(ActorRepository daoActor, ContactosRepository daoContactos) {
//		return args -> {
//			System.out.println("🚀 [Hilo: %s] Iniciando CommandLineRunner demo".formatted(Thread.currentThread().getName()));
//			System.err.println(daoActor.findById(1));
//			System.err.println(daoContactos.findById(1));
////			daoActor.save(new Actor());
//		};
//	} 
	
//	@Autowired
//	TaskScheduler taskScheduler;
//	
//	int cont = 0;
//	
//	@Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 5)
//	void scheduledTask() {
//		System.err.println("Scheduled task executed %d every 30 seconds".formatted(++cont));
//		if(cont >= 10 && taskScheduler instanceof ThreadPoolTaskScheduler scheduler) {
//			scheduler.shutdown();
//			System.err.println("Scheduled task stopped after %d executions.".formatted(cont));
//			System.exit(0);
//		}
//	}

//	@Bean
//	CommandLineRunner sakila(ActorRepository daoActor) {
//		return  args -> {
//			System.err.println("Acceso al datasource sakila");
//			System.err.println(daoActor.findById(2));
////			daoActor.findAllBy(ActorEdit.class).forEach(System.out::println);
//		};
//	}
//
//	@Bean
//	CommandLineRunner cursos(ContactosRepository daoContactos) {
//		return  args -> {
//			System.err.println("Acceso al datasource cursos");
//			System.err.println(daoContactos.findById(2));
////			daoContactos.findAll().forEach(System.out::println);
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
	@Autowired
	ContactosRepository daoContactos;
	
	@Override
	@Transactional
	@Async
	public void run(String... args) throws Exception {
		System.err.println("Inicia");
		System.out.println("🚀 [Hilo: %s] Iniciando CommandLineRunner run".formatted(Thread.currentThread().getName()));
//		listaTodos(daoContactos);
//		System.err.println("No espero");
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
	
	@Async
	void listaTodos(ContactosRepository dao) {
		System.out.println("🚀 [Hilo: %s] Iniciando Procesamiento de datos grandes.".formatted(Thread.currentThread().getName()));
        try {
            Thread.sleep(10000); // 10 segundos
			System.out.println("Lista de contactos:");
			dao.findAll().forEach(System.out::println);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}
	
//	@Bean
//	JavaMailSender mailSender() {
//		return new JavaMailSenderImpl();
////		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////		mailSender.setHost("localhost");
////		return mailSender;
//	}

//	@Bean // this is a template message that we can pre-load with default state
//	SimpleMailMessage templateMessage() {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("customerservice@mycompany.example");
//		message.setSubject("Your order");
//		return message;
//	}

}
