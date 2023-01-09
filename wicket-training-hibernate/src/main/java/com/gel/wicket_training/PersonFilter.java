package com.gel.wicket_training;
import java.io.Serializable;
import java.util.Date;

public class PersonFilter implements Serializable
{
    private String firstName;
    private String lastName;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
   
}
