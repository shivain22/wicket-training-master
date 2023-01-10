package com.gel.wicket_training.spring_boot.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table
public class PersonAddress implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "address_line", nullable = false, length = 100)
	private String addressLine;
	
	@Column(name = "city", nullable = false, length = 100)
	private String city;
	
	@Column(name = "state", nullable = false, length = 100)
	private String state;
	
	@Column(name = "pin", nullable = false, length = 100)
	private String pin;
	
	@Column(name = "country", nullable = false, length = 100)
	private String country;
	
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
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressLine == null) ? 0 : addressLine.hashCode());
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
		PersonAddress other = (PersonAddress) obj;
		if (addressLine == null) {
			if (other.addressLine != null)
				return false;
		} else if (!addressLine.equals(other.addressLine))
			return false;
		return true;
	}

}
