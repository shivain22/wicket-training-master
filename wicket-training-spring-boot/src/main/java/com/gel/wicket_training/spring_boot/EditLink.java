package com.gel.wicket_training.spring_boot;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.gel.wicket_training.spring_boot.entities.Person;

public class EditLink extends Link<Void> {

	ListItem<Person> listItem;
	Person person;
	public EditLink(String id,ListItem<Person> listItem) {
		super(id);
		this.listItem= listItem;
		this.person=listItem.getModelObject();
	}
	public EditLink(String id,Person person) {
		super(id);
		this.person=person;
	}
	
	@Override
	public void onClick() {
		PageParameters pp = new PageParameters();
		pp.add("person_id", person.getId());
		setResponsePage(BasicCrudPage.class,pp);
	}
}
