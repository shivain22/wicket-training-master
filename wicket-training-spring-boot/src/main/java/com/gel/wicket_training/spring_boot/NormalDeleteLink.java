package com.gel.wicket_training.spring_boot;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class NormalDeleteLink extends Link<Person> {

	ListItem item;
	Person person;
	PersonList personList;
    @SpringBean
    IPersonRepository personRepository;
	public NormalDeleteLink(String id, ListItem<Person> item,PersonList personList) {
		super(id);
		Injector.get().inject(this);
		this.item = item;
		this.person=item.getModelObject();
		this.personList = personList;
	}
	
	public NormalDeleteLink(String id,Person person) {
		super(id);
		this.person = person;
	}

	
	@Override
	public void onClick() {
		person = personRepository.findById(person.getId()).get();
		personRepository.delete(person);
		List<Person> persons = personRepository.findAll();
		personList.setPersons(persons);
	}

}
