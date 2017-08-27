package com.bsw.adel.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bsw.adel.dao.GenericDAO;

public class GenericDAOImpl<E, K extends Serializable> implements GenericDAO<E, K>{

	@PersistenceContext	
	protected EntityManager em;	
	
	protected Class<? extends E> daoType;

    /**
     * By defining this class as abstract, we prevent Spring from creating instance of this class
     * If not defined abstract getClass().getGenericSuperClass() would return Object.
     * There would be exception because Object class does not have constructor with parameters.
     */
    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Create new entity
     */
    public E create(E entity) {
        this.em.persist(entity);
        return entity;
    }

    /**
     * Update the entity
     * @param entity : the {@link Entity}
     * @return entity : the {@link Entity}
     */
    public E update(E entity) {
        this.em.merge(entity);
        return entity;
    }

    /**
     * delete the entity
     * @param entity : the {@link Entity}
     */
    public void delete(K key) {
    	this.em.remove(this.em.getReference(daoType, key));
    }

    /**
     * Get entity by id  entity
     * @param key : the id of {@link Entity}
     * @return entity : the {@link Entity}
     */
    public E find(K key) {
    	return (E) this.em.find(daoType, key);
    }

    /**
     * Get all {@link Entity} depending to the specified parameters
     * @param params : the specified parameters
     * @return {@link List} of the {@link Entity} 
     */
    @Override
    public List<E> getAll(final Map<String, Object> params) {
    	final StringBuffer queryString = new StringBuffer(
                "SELECT o from ");

        queryString.append(daoType.getSimpleName()).append(" o ");
        queryString.append(this.getQueryClauses(params, null));

        final Query query = this.em.createQuery(queryString.toString());

        return query.getResultList();
    }
    
    /**
     * Get the count of  all {@link Entity} depending to the specified parameters
     * @param params : the specified parameters
     * @return {@link List} of the {@link Entity} 
     */
    @Override
    public long countAll(final Map<String, Object> params) {

        final StringBuffer queryString = new StringBuffer(
                "SELECT count(o) from ");

        queryString.append(daoType.getSimpleName()).append(" o ");
        queryString.append(this.getQueryClauses(params, null));

        final Query query = this.em.createQuery(queryString.toString());

        return (Long) query.getSingleResult();
    }
    
    /**
	 * Constructs the sql "WHERE" clause and "ORDER BY" clause  based on the provided parameters<br>
	 * <b> WHERE CLAUSE</b><br>
	 * if param is a number n, append <b>"param = n"</b><br> 
	 * if param is a boolean, append <b>"param = true or false"</b><br>
	 * if param is a string str, append <b>"param = str" </b>etc...<br> 
	 * <b> ORDER BY CLAUSE </b><br>
	 * append <b> ORDER BY field_1, field_2 </b> etc..
     * @param params
     * @param orderParams
     * @return 
     */
    private String getQueryClauses(final Map<String, Object> params,
			final Map<String, Object> orderParams) {
		final StringBuffer queryString = new StringBuffer();
		if ((params != null) && !params.isEmpty()) {
			queryString.append(" where ");
			
			for (final Iterator<Map.Entry<String, Object>> it = params
					.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				if (entry.getValue() instanceof Boolean) {
					queryString.append(entry.getKey()).append(" is ")
							.append(entry.getValue()).append(" ");
				} else {
					if (entry.getValue() instanceof Number) {
						queryString.append(entry.getKey()).append(" = ")
								.append(entry.getValue());
					} else {
						// string equality
						queryString.append(entry.getKey()).append(" = '")
								.append(entry.getValue()).append("'");
					}
				}
				if (it.hasNext()) {
					queryString.append(" and ");
				}
			}
		}
		if ((orderParams != null) && !orderParams.isEmpty()) {
			queryString.append(" order by ");
			for (final Iterator<Map.Entry<String, Object>> it = orderParams
					.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				queryString.append(entry.getKey()).append(" ");
				if (entry.getValue() != null) {
					queryString.append(entry.getValue());
				}
				if (it.hasNext()) {
					queryString.append(", ");
				}
			}
		}
		return queryString.toString();
	}

}