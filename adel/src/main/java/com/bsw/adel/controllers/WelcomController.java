package com.bsw.adel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bsw.adel.model.Person;
import com.bsw.adel.service.PersonService;

/**
 * 
 * @author Aliou
 *
 */


@RestController
public class WelcomController {
	
	@Autowired
	PersonService personservice;
	
	@RequestMapping("/welcom")
	public String welcom(){
		return "rien";
	}
	
	@RequestMapping(value="/person/{name}", method = RequestMethod.GET, produces = "application/json")
	public Person loadUserByUsername(@PathVariable("name") String name){
		return personservice.loadUserByUsername(name);	
	}
	
	@PutMapping("/person")
	public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
		System.out.println(person.toString());
		personservice.updatePerson(person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
}
