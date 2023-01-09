package com.gel.wicket_training;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.service.PersonService;

public class DeleteLink extends AjaxLink<Person> {

	ListItem item;
	Person person;
	PersonList personList;
	public DeleteLink(String id, ListItem<Person> item,PersonList personList) {
		super(id);
		this.item = item;
		this.person=item.getModelObject();
		this.personList = personList;
	}
	
	public DeleteLink(String id,Person person) {
		super(id);
		this.person = person;
	}

	
	@Override
	public void onClick(AjaxRequestTarget target) {
		// TODO Auto-generated method stub
		PersonService ps = new PersonService();
		ps.openSession();
		person = ps.findById(person.getId());
		ps.delete(person);
		ps.closeSession();
		ps.openSession();
		List<Person> persons = ps.findAll();
		personList.setPersons(persons);
		ps.closeSession();
		target.add(getParent().getParent().getParent());
	}

}
