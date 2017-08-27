package com.bsw.adel.service;

import com.bsw.adel.model.Person;

public interface PersonService {
	
	public Person loadUserByUsername(String name);
	public void updatePerson(Person person);
}
