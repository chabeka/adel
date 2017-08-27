package com.bsw.adel.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO <E,K> {
    public E create(E entity) ;
    public E update(E entity) ;
    public void delete(K key);
    public E find(K key);
	public long countAll(Map<String, Object> params);
	public List<E> getAll(Map<String, Object> params);
}
