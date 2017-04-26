package com.asseco.repository;

import java.util.Date;

/**
 * Fabryka sluzaca do tworzenia encji osoby.
 * 
 * TODO Klasy nalezy zaimplementowac w ten sposob aby tylko i wylacznie mozna bylo tworzyc instancje klasy Person za pomoca fabryki
 */
public interface PersonFactory {

	/**
	 * Tworzy osobe
	 * @param name imie
	 * @param surname nazwisko
	 * @param birthDate data urodzenia
	 * @return Utworzona osoba
	 */
	Person create(String name, String surname, Date birthDate);
	
}
