package com.gel.wicket_training.spring_boot;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class BasicCrudPage extends HomePage {
	
	PersonList personList;
    @SpringBean
	IPersonRepository personRepository;
	public BasicCrudPage(final PageParameters page) throws Exception{

		List<Person> persons = personRepository.findAll();
		List<StringValue> personIds = page.getValues("person_id");
		Person person=new Person();
		if(personIds.size()>0) {
		 person = personRepository.findById(personIds.get(0).toLong()).get();
		}
		AddEditForm addEditForm = new AddEditForm("addForm", person);
		PersonList personList= new PersonList("personList", persons);
		personList.setOutputMarkupId(true);
		addEditForm.setOutputMarkupId(true);
		addEditForm.add(personList);
		add(addEditForm);
	}
}
