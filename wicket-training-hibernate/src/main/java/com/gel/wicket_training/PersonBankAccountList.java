package com.gel.wicket_training;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.gel.wicket_training.entities.PersonBankAccount;

public class PersonBankAccountList extends ListView<PersonBankAccount> {

	public PersonBankAccountList(String id,Set<PersonBankAccount> model) {
		super(id,model.stream().collect(Collectors.toList()));
	}
	
	public PersonBankAccountList(String id,List<PersonBankAccount> model) {
		super(id,model);
	}

	@Override
	protected void populateItem(ListItem<PersonBankAccount> item) {
		PersonBankAccount pba = item.getModelObject();
		item.add(new MultiLineLabel("bankAccount",pba.getBankName()+"\n"+pba.getAccountNumber()));
		
	}

	
}
