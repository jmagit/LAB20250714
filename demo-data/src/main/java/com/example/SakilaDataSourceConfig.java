package com.example;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.contracts.domain.repositories.sakila.FilmRepository;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.contracts.domain.repositories.sakila", // Paquete de los repositorios primarios
    entityManagerFactoryRef = "sakilaEntityManagerFactory",
    transactionManagerRef = "sakilaTransactionManager"
)
public class SakilaDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.sakila") // Prefijo de las propiedades en application.properties
    DataSourceProperties sakilaDataSourceProperties() {
        return new DataSourceProperties();
    }
	
//    @Primary
    @Bean(name = "sakilaDataSource")
    DataSource sakilaDataSource(DataSourceProperties sakilaDataSourceProperties) {
        return sakilaDataSourceProperties.initializeDataSourceBuilder().build();
    }

//    @Primary
    @Bean(name = "sakilaEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean sakilaEntityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource sakilaDataSource) {
        return builder
                .dataSource(sakilaDataSource)
                .packages("com.example.domain.entities.sakila") // Paquete de las entidades primarias
                .persistenceUnit("sakilaPU")
                .build();
    }

    @Primary
    @Bean(name = "sakilaTransactionManager")
    PlatformTransactionManager sakilaTransactionManager(EntityManagerFactory sakilaEntityManagerFactory) {
        return new JpaTransactionManager(sakilaEntityManagerFactory);
    }

}
