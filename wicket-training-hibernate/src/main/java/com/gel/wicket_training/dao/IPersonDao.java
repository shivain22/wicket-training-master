package com.gel.wicket_training.dao;
import java.util.List;

public interface IPersonDao<T, Long> {

	public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Long id);
	
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
}