package com.gel.wicket_training.spring_boot;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import com.gel.wicket_training.spring_boot.entities.PersonMobileNumber;

public class PersonMobileNumberList extends ListView<PersonMobileNumber> {
	
	public PersonMobileNumberList(String id,List<PersonMobileNumber> model) {
		super(id,model);
	}
	
	@Override
	protected void populateItem(ListItem<PersonMobileNumber> item) {
		PersonMobileNumber pmn = item.getModelObject();
		item.add(new Label("mobileNumber",Model.of(pmn.getMobileNumber())));
	}

}
