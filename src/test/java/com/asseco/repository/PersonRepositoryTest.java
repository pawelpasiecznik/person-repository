package com.asseco.repository;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.asseco.repository.PersonRepository.PersonNotFoundException;

/*
 * 	TODO Dla zaimplementowany klas nalezy wykonac ponizszy test. Wazne jest aby funckje byly napisane optymalnie i miescily sie w wyznaczonych czasach.
 */
public class PersonRepositoryTest {

	/*
	 * TODO Wstrzyknac referencje zaimplementowanych klas do ponizszych zmiennych
	 */
	private static PersonRepository repository = null;
	private static PersonFactory factory = null;
	
	private static final int REPO_SIZE = 1000000;
	
	@BeforeClass
	public static void initialize() {
		repository.save(factory.create(null, null, null));
		repository.save(factory.create(null, null, null));
		repository.save(factory.create(null, null, null));
		repository.save(factory.create(null, null, null));
		repository.save(factory.create(null, getSurnameById(0), null));
		
		for(int i = 1 ; i < REPO_SIZE / 5; i++) {
			for(int j = 0 ; j < 5; j++) {
				Person person = factory.create(
						getNameById(i),
						getSurnameById(i),
						new Date()
				);
				repository.save(person);
			}
		}
	}
	
	@Test
	public void testFindAll() {
		
		Collection<Person> persons = repository.findAll();
		Assert.assertEquals(REPO_SIZE, persons.size());
		
		persons.clear();
		
		persons = repository.findAll();
		Assert.assertEquals(REPO_SIZE, persons.size());
	}
	
	@Test(timeout=2)
	public void testFindOneWhenPersonExistsThenSholudReturn() {
		Long idx = REPO_SIZE / 10L;
		Person person = repository.findOne(idx);
		Assert.assertEquals(idx, person.getId());
	}
	
	@Test(timeout=2, expected=PersonNotFoundException.class)
	public void testFindOneWhenPersonNotExistsThenSholudThrowException() {
		repository.findOne(-1);
	}
	
	
	@Test(timeout=2)
	public void testFindByNameAndSurname() {
		int idx = REPO_SIZE / 10;
		String name = getNameById(idx);
		String surname = getSurnameById(idx);
		
		Collection<Person> persons = repository.findByNameAndSurname(name, surname);
		Assert.assertEquals(5, persons.size());
		
		for(Person p : persons) {
			Assert.assertEquals(name, p.getName());
			Assert.assertEquals(surname, p.getSurname());
		}
	}
	
	@Test(timeout=4)
	public void testFindBySurnameBetweenOrderBySurnameWhenFromParamIsNullThenShouldReturnFromBegin() {
		Collection<Person> persons =  repository.findBySurnameBetweenOrderBySurname(null, getSurnameById(0));
		Assert.assertEquals(5, persons.size());
	}
	
	@Test(timeout=4)
	public void testFindBySurnameBetweenOrderBySurnameWhenToParamIsNullThenShouldReturnToEnd() {
		Collection<Person> persons =  repository.findBySurnameBetweenOrderBySurname(getSurnameById((REPO_SIZE / 5) - 1), null);
		Assert.assertEquals(5, persons.size());
	}
	
	@Test(timeout=4)
	public void testFindBySurnameBetweenOrderBySurname() {
		final int size = 20;
		final int fromIdx = REPO_SIZE / 10;
		final int toIdx = fromIdx + size / 5 - 1;
		
		Collection<Person> persons =  repository.findBySurnameBetweenOrderBySurname(getSurnameById(fromIdx), getSurnameById(toIdx));
		Assert.assertEquals(size, persons.size());
		
		String previousValue = "";
		for(Person person : persons) {
			Assert.assertTrue(previousValue.compareTo(person.getSurname()) <= 0);
			previousValue.equals(person.getSurname());
		}
	}
	
	private static String getNameById(int i) {
		return String.format("Jan%010d", i);
	}
	
	private static String getSurnameById(int i) {
		return String.format("Nowak%010d", i);
	}
	
}
