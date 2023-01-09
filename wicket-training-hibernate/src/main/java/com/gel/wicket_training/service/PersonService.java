package com.gel.wicket_training.service;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.hibernate.Session;

import com.gel.wicket_training.PersonFilter;
import com.gel.wicket_training.dao.PersonDao;
import com.gel.wicket_training.entities.Person;

public class PersonService implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -1441487094322189099L;
	private static PersonDao personDao;
 
    public PersonService() {
    	personDao = new PersonDao();
    }
    
    public Session openSession() {
    	return personDao.openCurrentSessionwithTransaction();
    }
    
    public void closeSession() {
    	personDao.closeCurrentSessionwithTransaction();
    }
    
	public Person persist(Person entity) {
        personDao.persist(entity);
        return entity;
    }
	public Person merge(Person entity) {
        personDao.merge(entity);
        return entity;
    }
 
    public void update(Person entity) {
        personDao.update(entity);
    }
    
    public Person findById(Long id) {
        return  personDao.findById(id);
    }
 
    public void delete(Person person) {
        personDao.delete(person);
    }
 
    public List<Person> findAll() {
        return personDao.findAll();
    }
    
    public Integer countAll() {
        return personDao.countAll();
    }
    
    public List<Person> findAll(SortParam s, int first, int count) {
    	String sortOrder = "asc";
    	if(!s.isAscending()) {
    		sortOrder="desc";
    	}
        return personDao.findAll(sortOrder,s.getProperty().toString(), first, count);
    }
    
    public List<Person> findAll(SortParam s, int first, int count,PersonFilter personFilter) {
    	String sortOrder = "asc";
    	if(!s.isAscending()) {
    		sortOrder="desc";
    	}
        return personDao.findAll(sortOrder,s.getProperty().toString(), first, count,personFilter);
    }
    
    public Long countAll(SortParam s, int first, int count,PersonFilter personFilter) {
    	String sortOrder = "asc";
    	if(!s.isAscending()) {
    		sortOrder="desc";
    	}
        return personDao.countAll(sortOrder,s.getProperty().toString(), first, count,personFilter);
    }
 
    public void deleteAll() {
        personDao.deleteAll();
    }
 
    public PersonDao personDao() {
        return personDao;
    }
}