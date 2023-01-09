package com.gel.wicket_training.table;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.service.PersonService;

public class DataTablePage extends WebPage {

	PersonService personService = new PersonService();
	
	public DataTablePage() {
		List<Person> persons = personService.findAll(); 
	}

}
