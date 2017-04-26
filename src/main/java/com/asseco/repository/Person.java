package com.asseco.repository;

import java.util.Date;

/**
 * Struktura osoby
 * 
 * TODO Zaimplementowac strukture osoby
 */
public interface Person {

	void setId(Long id);
	
	Long getId();

	String getName();

	String getSurname();

	Date getBirthDate();

	Date getCreationDate();
	
}
