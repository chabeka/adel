package com.bsw.adel.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bsw.adel.dao.IPersonDAO;
import com.bsw.adel.model.Person;

@Repository
public class PersonDAOImpl extends GenericDAOImpl<Person, Long> implements IPersonDAO{

	@Override
	public Person loadUserByUsername(String name) {
		Query query = this.em
                .createQuery("select p FROM Person p where p.name= :name");
        query.setParameter("name", name);
        List<Person> persons = query.getResultList();
        if (persons != null && persons.size() == 1) {
            return persons.get(0);
        }
        return null;
	}

}
