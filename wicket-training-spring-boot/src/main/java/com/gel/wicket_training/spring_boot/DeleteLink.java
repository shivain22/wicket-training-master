package com.gel.wicket_training.spring_boot;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class DeleteLink extends AjaxLink<Person> {

	ListItem item;
	Person person;
    @SpringBean
    IPersonRepository personRepository;
	PersonList personList;
	public DeleteLink(String id, ListItem<Person> item,PersonList personList) {
		super(id);
		Injector.get().inject(this);
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
		person = personRepository.findById(person.getId()).get();
		personRepository.delete(person);
		List<Person> persons = personRepository.findAll();
		personList.setPersons(persons);
		target.add(getParent().getParent().getParent());
	}

}
