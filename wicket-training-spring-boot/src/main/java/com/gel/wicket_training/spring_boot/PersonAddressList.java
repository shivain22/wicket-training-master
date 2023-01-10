package com.gel.wicket_training.spring_boot;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.gel.wicket_training.spring_boot.entities.PersonAddress;

public class PersonAddressList extends ListView<PersonAddress> {

	public PersonAddressList(String id,Set<PersonAddress> model) {
		super(id,model.stream().collect(Collectors.toList()));
	}
	public PersonAddressList(String id,List<PersonAddress> model) {
		super(id,model);
	}

	@Override
	protected void populateItem(ListItem<PersonAddress> item) {
		PersonAddress pa = item.getModelObject();
		item.add(new MultiLineLabel("address",
				pa.getAddressLine()+"\n"
			   +pa.getCity()+"\n"
			   +pa.getState()+"\n"
			   +pa.getCountry()+"\n"
			   +pa.getPin()));
	}

}
