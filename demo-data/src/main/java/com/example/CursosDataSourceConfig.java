package com.example;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.contracts.domain.repositories.cursos.ContactosRepository;
import com.example.domains.entities.cursos.Contacto;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
//    basePackages = "com.example.contracts.domain.repositories.cursos", // Paquete de los repositorios primarios
    basePackageClasses = ContactosRepository.class, // Paquete de los repositorios primarios
    entityManagerFactoryRef = "cursosEntityManagerFactory",
    transactionManagerRef = "cursosTransactionManager"
)
public class CursosDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.cursos") // Prefijo de las propiedades en application.properties
    DataSourceProperties cursosDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "cursosDataSource")
    DataSource cursosDataSource(DataSourceProperties cursosDataSourceProperties) {
        return cursosDataSourceProperties.initializeDataSourceBuilder().build();
    }
    
	@Bean
	JpaVendorAdapter cursosJpaVendorAdapter(JpaProperties jpaProperties, DataSource cursosDataSource) {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(jpaProperties.isShowSql());
		return adapter;
	}

	@Bean
	EntityManagerFactoryBuilder cursosEntityManagerFactoryBuilder(JpaVendorAdapter cursosJpaVendorAdapter,
			ObjectProvider<PersistenceUnitManager> persistenceUnitManager, JpaProperties jpaProperties) {
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(cursosJpaVendorAdapter,
				dataSource -> jpaProperties.getProperties(), persistenceUnitManager.getIfAvailable());
		return builder;
	}

    @Bean(name = "cursosEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean cursosEntityManagerFactory(
            EntityManagerFactoryBuilder cursosEntityManagerFactoryBuilder, DataSource cursosDataSource) {
        return cursosEntityManagerFactoryBuilder
                .dataSource(cursosDataSource)
                .packages(Contacto.class) // Paquete de las entidades primarias
//                .packages("com.example.domain.entities.cursos") // Paquete de las entidades primarias
                .persistenceUnit("cursosPU")
                .build();
    }

    @Bean(name = "cursosTransactionManager")
    PlatformTransactionManager cursosTransactionManager(@Qualifier("cursosEntityManagerFactory") EntityManagerFactory cursosEntityManagerFactory) {
        return new JpaTransactionManager(cursosEntityManagerFactory);
    }

}
