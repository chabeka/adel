package com.bsw.adel.dao;

import com.bsw.adel.model.Person;

public interface IPersonDAO extends GenericDAO<Person, Long> {
	
	/**
     * Returns an Person object that matches the name given
     *
     * @param name : the name of {@link Person}
     * @return : the {@link Person} matched
     */
    public Person loadUserByUsername(String name);
}
