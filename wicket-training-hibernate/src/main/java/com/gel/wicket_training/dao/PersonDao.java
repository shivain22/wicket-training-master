package com.gel.wicket_training.dao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.gel.wicket_training.PersonFilter;
import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.hibernate.HibernateUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



public class PersonDao implements IPersonDao<Person, Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935736127458752315L;

	private Session currentSession;
	
	private Transaction currentTransaction;

	public PersonDao() {
		
	}

	public Session openCurrentSession() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public void persist(Person entity) {
		getCurrentSession().persist(entity);
	}

	public void update(Person entity) {
		getCurrentSession().update(entity);
	}

	public Person findById(Long id) {
		Person person = (Person) getCurrentSession().get(Person.class, id);
		return person; 
	}

	public void delete(Person entity) {
		getCurrentSession().remove(entity);
	}
	
	public Integer countAll() {
		Long count = getCurrentSession().createQuery("select count(*) from Person p",Long.class).getSingleResult();
		return count.intValue();
	}

	public List<Person> findAll() {
		Query<Person> query = getCurrentSession().createQuery("from Person",Person.class);
		List<Person> persons = query.list();
		return persons;
	}
	
	public List<Person> findAll(String orderBy, String column, int first, int count) {
		Query<Person> query = getCurrentSession().createQuery("from Person p order by p."+column+" "+orderBy,Person.class);
		query.setFirstResult(first);
		query.setMaxResults(count);
		List<Person> persons = query.list();
		return persons;
	}
	
	public List<Person> findAll(String orderBy, String column, int first, int count,PersonFilter personFilter) {
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();
		CriteriaQuery<Person> cr = cb.createQuery(Person.class);
		Root<Person> root = cr.from(Person.class);
		if(personFilter!=null) {
			if(personFilter.getFirstName()!=null && personFilter.getFirstName().trim().length()>0) {
				Predicate p = cb.like(root.get("firstName"), "%"+personFilter.getFirstName()+"%");
				predicates.add(p);
			}
			if(personFilter.getLastName()!=null && personFilter.getLastName().trim().length()>0) {
				Predicate p = cb.like(root.get("lastName"), "%"+personFilter.getLastName()+"%");
				predicates.add(p);
			}
		}
		if(predicates.size()>0) {
			cr.where(predicates.toArray(new Predicate[] {}));
		}
		if(orderBy.equals("asc"))
			cr.orderBy(cb.asc(root.get(column)));
		else
			cr.orderBy(cb.desc(root.get(column)));
		cr.select(root);
		Query<Person> query = getCurrentSession().createQuery(cr);
		query.setFirstResult(first);
		query.setMaxResults(count);
		List<Person> persons = query.getResultList();
		return persons;
	}
	
	public Long countAll(String orderBy, String column, int first, int count,PersonFilter personFilter) {
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<Person> root = cr.from(Person.class);
		if(personFilter!=null) {
			if(personFilter.getFirstName()!=null && personFilter.getFirstName().trim().length()>0) {
				Predicate p = cb.like(root.get("firstName"), "%"+personFilter.getFirstName()+"%");
				predicates.add(p);
			}
			if(personFilter.getLastName()!=null && personFilter.getLastName().trim().length()>0) {
				Predicate p = cb.like(root.get("lastName"), "%"+personFilter.getLastName()+"%");
				predicates.add(p);
			}
		}
		if(predicates.size()>0) {
			cr.where(predicates.toArray(new Predicate[] {}));
		}
		if(orderBy.equals("asc"))
			cr.orderBy(cb.asc(root.get(column)));
		else
			cr.orderBy(cb.desc(root.get(column)));
		cr.select(cb.count(root));
		Query<Long> query = getCurrentSession().createQuery(cr);
		List<Long> totalRows = query.getResultList();
		return totalRows.get(0);
	}

	public void deleteAll() {
		List<Person> entityList = findAll();
		for (Person entity : entityList) {
			delete(entity);
		}
	}
	public void merge(Person person) {
		getCurrentSession().merge(person);
	}
}