package com.gel.wicket_training.spring_boot.entities;

import org.apache.wicket.model.IModel;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
@Table
@Data
public class Person implements Serializable,IModel<Person>  {
	
	public Person() {
		this.firstName="";
		this.lastName="";
	}

	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	
	@Column(nullable = false, length = 100)
	private String lastName;
	
	@OneToMany(mappedBy="person",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PersonMobileNumber> personMobileNumbers = new HashSet<>(10);
	
	@OneToMany(mappedBy="person",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PersonEmail> personEmails = new HashSet<PersonEmail>(10);
	
	@OneToMany(mappedBy="person",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PersonAddress> personAddresses = new HashSet<PersonAddress>(10);

	@OneToMany(mappedBy="person",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PersonBankAccount> personBankAccounts = new HashSet<PersonBankAccount>(10);
	

	
	
	public boolean addPersonMobileNumber(PersonMobileNumber personMobileNumber) {
		if(this.personMobileNumbers!=null) {
			personMobileNumber.setPerson(this);
			return personMobileNumbers.add(personMobileNumber);
		}
		return false;
	}
	
	public boolean addPersonEmail(PersonEmail personEmail) {
		if(this.personEmails!=null) {
			personEmail.setPerson(this);
			return personEmails.add(personEmail);
		}
		return false;
	}
	
	public boolean addPersonAddress(PersonAddress personAddress) {
		if(this.personAddresses!=null) {
			personAddress.setPerson(this);
			return personAddresses.add(personAddress);
		}
		return false;
	}
	
	
	public boolean removePersonMobileNumber(PersonMobileNumber personMobileNumber) {
		if(this.personMobileNumbers!=null && personMobileNumbers.contains(personMobileNumber)) {
			personMobileNumber.setPerson(null);
			return personMobileNumbers.remove(personMobileNumber);
		}
		return false;
	}
	
	public boolean removePersonEmail(PersonEmail personEmail) {
		if(this.personEmails!=null && personEmails.contains(personEmail)) {
			personEmail.setPerson(null);
			return personEmails.remove(personEmail);
		}
		return false;
	}
	
	public boolean removePersonAddress(PersonAddress personAddress) {
		if(this.personAddresses!=null && personAddresses.contains(personAddress)) {
			personAddress.setPerson(null);
			return personAddresses.remove(personAddress);
		}
		return false;
	}
	
	
	public boolean addPersonBankAccount(PersonBankAccount personBankAccount) {
		if(this.personBankAccounts!=null) {
			personBankAccount.setPerson(this);
			return personBankAccounts.add(personBankAccount);
		}
		return false;
	}
	
	
	public boolean removePersonBankAccount(PersonBankAccount personBankAccount) {
		if(this.personBankAccounts!=null && personBankAccounts.contains(personBankAccount)) {
			personBankAccount.setPerson(null);
			return personBankAccounts.remove(personBankAccount);
		}
		return false;
	}

	public void removeAllCollections() {
		Iterator<PersonMobileNumber> it = personMobileNumbers.iterator();
		while(it.hasNext()){
			PersonMobileNumber pmn = it.next();
			pmn.setPerson(null);
			it.remove();
		}
		
		Iterator<PersonEmail> it1 = personEmails.iterator();
		while(it1.hasNext()){
			PersonEmail pe = it1.next();
			pe.setPerson(null);
			it1.remove();
		}
		Iterator<PersonAddress> it2 = personAddresses.iterator();
		while(it2.hasNext()){
			PersonAddress pa = it2.next();
			pa.setPerson(null);
			it2.remove();
		}
		Iterator<PersonBankAccount> it3 = personBankAccounts.iterator();
		while(it3.hasNext()){
			PersonBankAccount pba=it3.next();
			pba.setPerson(null);
			it3.remove();
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	@Override
	public Person getObject() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
}
