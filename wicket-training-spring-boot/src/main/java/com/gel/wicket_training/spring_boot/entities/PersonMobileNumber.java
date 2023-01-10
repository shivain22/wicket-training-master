package com.gel.wicket_training.spring_boot.entities;

import org.apache.wicket.model.IModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class PersonMobileNumber implements Serializable,IModel<PersonMobileNumber>   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "mobile_number", nullable = false, length = 100)
	private String mobileNumber;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
	private Person person;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		PersonMobileNumber other = (PersonMobileNumber) obj;
		
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
	
		return true;
	}
	@Override
	public PersonMobileNumber getObject() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public String toString() {
		return mobileNumber;
	}
	
}
