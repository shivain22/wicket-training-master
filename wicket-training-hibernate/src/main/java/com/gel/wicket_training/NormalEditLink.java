package com.gel.wicket_training;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.gel.wicket_training.entities.Person;

public class NormalEditLink extends Link<Void> {

	ListItem<Person> listItem;
	Person person;
	public NormalEditLink(String id,ListItem<Person> listItem) {
		super(id);
		this.listItem= listItem;
		this.person=listItem.getModelObject();
	}
	public NormalEditLink(String id,Person person) {
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
