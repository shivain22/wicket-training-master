package com.gel.wicket_training.spring_boot;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import com.gel.wicket_training.spring_boot.entities.PersonEmail;

public class PersonEmailList extends ListView<PersonEmail> {
	
	public PersonEmailList(String id,List<PersonEmail> model) {
		super(id,model);
	}
	
	@Override
	protected void populateItem(ListItem<PersonEmail> item) {
		PersonEmail pem = item.getModelObject();
		item.add(new Label("email",Model.of(pem.getEmail())));
	}

}
