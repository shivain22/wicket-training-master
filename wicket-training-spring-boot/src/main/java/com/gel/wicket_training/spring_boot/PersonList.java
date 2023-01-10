package com.gel.wicket_training.spring_boot;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.entities.PersonEmail;
import com.gel.wicket_training.spring_boot.entities.PersonMobileNumber;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class PersonList extends ListView<Person> {

	@SpringBean
    IPersonRepository personRepository;
	public PersonList(String id,List<Person> persons) {
		super(id,persons);
		Injector.get().inject(this);
	}

	@Override
	protected void populateItem(ListItem<Person> item) {
		Person person = item.getModelObject();
		person = personRepository.findById(person.getId()).get();
		item.add(new Label("lblFirstName",person.getFirstName()));
		item.add(new Label("lblLastName",person.getLastName()));
		List<PersonMobileNumber> mobileNumbers = new ArrayList<>();
		for(PersonMobileNumber pmn:person.getPersonMobileNumbers()) {
			mobileNumbers.add(pmn);
		}
		PersonMobileNumberList personMobileNumberList = new PersonMobileNumberList("mobileNumbers", mobileNumbers);
		item.add(personMobileNumberList);
		
		
		List<PersonEmail> emails = new ArrayList<>();
		for(PersonEmail pem:person.getPersonEmails()) {
			emails.add(pem);
		}
		PersonEmailList personEmailList = new PersonEmailList("emails", emails);
		PersonAddressList personAddressList = new PersonAddressList("addresses",person.getPersonAddresses());
		PersonBankAccountList personBankAccountList = new PersonBankAccountList("bankAccounts", person.getPersonBankAccounts());
		item.add(personAddressList);
		item.add(personEmailList);
		item.add(personBankAccountList);
		item.add(new EditLink("editLink", item));
		item.add(new DeleteLink("deleteLink",item,this));
	}
	
	

	public void setPersons(List<Person> persons) {
		super.setModelObject(persons);
	}

}
