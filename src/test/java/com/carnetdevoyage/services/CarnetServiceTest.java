package com.carnetdevoyage.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.carnetdevoyage.LesCarnetsDeVoyageJavaApplication;
import com.carnetdevoyage.config.SecurityConfig;
import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.repositories.CarnetRepository;
import com.carnetdevoyage.repositories.UserRepository;

import jakarta.persistence.EntityManager;


@SpringBootTest(classes = {LesCarnetsDeVoyageJavaApplication.class, SecurityConfig.class})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class, 
        DataSourceTransactionManagerAutoConfiguration.class, 
        HibernateJpaAutoConfiguration.class
    })
public class CarnetServiceTest {
	
	@MockBean
	private JwtService JwtService;
	
	@MockBean
	private EntityManager entityManager;
	
	@MockBean
	private CarnetRepository carnetRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	//Autowired car je l'utilise pour mon test, je mock tout le reste
	@Autowired
	private CarnetService carnetService;
	
	@BeforeEach
	void setUp() throws Exception {
		reset(carnetRepository);
	}
	
	@Test
	void testFindAll() {
		// arrange => prépare
		List<Carnet> FausseListeCarnets = List.of(
				Carnet.builder().id(1).build(),
				Carnet.builder().id(2).build(),
				Carnet.builder().id(3).build()
				);
		when(carnetRepository.findAll()).thenReturn(FausseListeCarnets);
		// act => fait
		 var result =  carnetService.findAll();
		// assert => vérifie
		 assertIterableEquals(FausseListeCarnets, result);
		 verify(carnetRepository, Mockito.times(1)).findAll();
		 verifyNoMoreInteractions(carnetRepository);
	}

}
