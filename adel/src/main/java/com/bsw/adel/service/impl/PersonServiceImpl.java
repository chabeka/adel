package com.bsw.adel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bsw.adel.dao.IPersonDAO;
import com.bsw.adel.model.Person;
import com.bsw.adel.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	IPersonDAO persondao;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Person loadUserByUsername(String name){
		return persondao.loadUserByUsername(name);	
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updatePerson(Person person) {
		persondao.update(person);		
	}
}
