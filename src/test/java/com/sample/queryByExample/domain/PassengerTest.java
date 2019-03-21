package com.sample.queryByExample.domain;


import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.queryByExample.repositories.PassengerRepository;

/**
 * 
 * @author EvelynVieira
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PassengerTest {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private PassengerRepository repository;
	
	@Before
	public void setUp() {
		
		entityManager.persist(Passenger.from(null, "Jill", "Smith", 50));
        entityManager.persist(Passenger.from(null, "Eve", "Jackson", 95));
        entityManager.persist(Passenger.from(null, "Fred", "Bloggs", 22));
        entityManager.persist(Passenger.from(null, "Ricki", "Bobbie", 36));
        entityManager.persist(Passenger.from(null, "Siya", "Kolisi", 85));
		
	}
	
	//@Test
	public void givenPassengers() {
		
		// O método estático Example.of() cria um Example usando ExampleMatcher.matching(), ou seja, é feita uma comparação exata em todas as propriedades não nulas de Passenger. A comparação é case sensitive para Strings.
		 Example<Passenger> example = Example.of(Passenger.from(null, "Fred", "Bloggs", 22));
		 
		 Optional<Passenger> actual = repository.findOne(example);
		 	 
		 assertTrue(actual.isPresent());
		 assertEquals(Passenger.from(3l, "Fred", "Bloggs", 22), actual.get());
		
	}
	
	//@Test
	public void givenPassengers_caseInsensitiveMatcher() {
		
		// Criando nosso próprio ExampleMatcher personalizamos o comportamento, nesse caso vamos obter uma correspondência insensível a maiúsculas e minúsculas.
		//ExampleMatcher.matchingAll() tem o mesmo comportamento de ExampleMatcher.matching()
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		
		Example<Passenger> example = Example.of(Passenger.from(null, "fred", "bloggs", 22),
			      caseInsensitiveExampleMatcher);
		
		Optional<Passenger> actual = repository.findOne(example);
		
		 assertTrue(actual.isPresent());
		 assertEquals(Passenger.from(3l, "Fred", "Bloggs", 22), actual.get());
		
	}
	
	//@Test
	public void givenPassenger_customMatcher() {
		
		Passenger jill = Passenger.from(1l, "Jill", "Smith", 50);
	    Passenger eve = Passenger.from(2l, "Eve", "Jackson", 95);
	    Passenger fred = Passenger.from(3l, "Fred", "Bloggs", 22);
	    Passenger siya = Passenger.from(4l, "Siya", "Kolisi", 85);
	    Passenger ricki = Passenger.from(5l, "Ricki", "Bobbie", 36);
	    
	    // Combinando propriedades
	    ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny().withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
	    
	    Example<Passenger> example = Example.of(Passenger.from(null, "e", "s", 95), customExampleMatcher);
	    
	    List<Passenger> passengers = repository.findAll(example);
	    
	    assertThat(passengers, Matchers.not(contains(ricki, jill)));
	    
	}
	
	@Test
	public void givenPassenger_ignoringMatcher() {
		
		Passenger jill = Passenger.from(1l, "Jill", "Smith", 50);
	    Passenger eve = Passenger.from(2l, "Eve", "Jackson", 95);
	    Passenger fred = Passenger.from(3l, "Fred", "Bloggs", 22);
	    Passenger siya = Passenger.from(4l, "Siya", "Kolisi", 85);
	    Passenger ricki = Passenger.from(5l, "Ricki", "Bobbie", 36);
	    
	    ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
	    	      .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
	    	      .withIgnorePaths("firstName", "seatNumber");
	    
	    //Passenger passenger = new Passenger(null, null, "b", null);
	    
	    Example<Passenger> example = Example.of(Passenger.from(null, null, "b", null), ignoringExampleMatcher);
	    
	    List<Passenger> passengers = repository.findAll(example);
	    
	    assertThat(passengers, Matchers.contains(fred, ricki));
	    assertThat(passengers, Matchers.not(Matchers.contains(jill)));
	    assertThat(passengers, Matchers.not(Matchers.contains(eve)));
	    assertThat(passengers, Matchers.not(Matchers.contains(siya)));
		
	}

}
