package com.gel.wicket_training.spring_boot.table;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class DataTablePage extends WebPage {

    @SpringBean
    IPersonRepository personRepository ;
	
	public DataTablePage() {
		List<Person> persons = personRepository.findAll();
	}

}
