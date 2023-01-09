package com.gel.wicket_training;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.service.PersonService;

public class BasicCrudPage extends IndexPage {
	
	PersonList personList;
	
	public BasicCrudPage(final PageParameters page) throws Exception{
		
		PersonService ps = new PersonService();
		ps.openSession();
		List<Person> persons = ps.findAll();
		List<StringValue> personIds = page.getValues("person_id");
		Person person=new Person();
		if(personIds.size()>0) {
		 person = ps.findById(personIds.get(0).toLong());
		}
		AddEditForm addEditForm = new AddEditForm("addForm", person);
		PersonList personList= new PersonList("personList", persons);
		personList.setOutputMarkupId(true);
		addEditForm.setOutputMarkupId(true);
		addEditForm.add(personList);
		add(addEditForm);
		ps.closeSession();
	}
}