package com.gel.wicket_training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.gel.wicket_training.customcomponent.ListEditor;
import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.entities.PersonAddress;
import com.gel.wicket_training.entities.PersonBankAccount;
import com.gel.wicket_training.entities.PersonEmail;
import com.gel.wicket_training.entities.PersonMobileNumber;
import com.gel.wicket_training.service.PersonService;

public class AddEditForm extends Form<AddEditForm> {

	private int actionCounts=1;
	public int getActionCounts() {
		return actionCounts;
	}
	public void setActionCounts(int actionCounts) {
		this.actionCounts = actionCounts;
	}

	
	
	TextField firstName;
	TextField lastName ;
	HiddenField personId;
	List<ListItem<PersonMobileNumber>> personMobileNumbers = new ArrayList<>();
	List<ListItem<PersonEmail>> personEmails = new ArrayList<>();
	List<ListItem<PersonAddress>> personAddresses = new ArrayList<>();
	List<ListItem<PersonBankAccount>> personBankAccounts = new ArrayList<>();
	
	ListEditor<ListItem<PersonMobileNumber>> personMobileNumberEditor;
	ListEditor<ListItem<PersonEmail>> personEmailEditor;
	ListEditor<ListItem<PersonAddress>> personAddressEditor;
	ListEditor<ListItem<PersonBankAccount>> personBankAccountEditor;
	 
	public AddEditForm(String id,Person person) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<>(this));
		Model<String> model = new Model<String>() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            private String currentTime = sdf.format(new Date());
            public String getObject() {
            	currentTime = sdf.format(new Date());
                return currentTime;
            }
        };
		
		Label currentTimeLbl = new Label("currentTime",model);
		Button currentTimeBtn = new Button("currentTimeBtn") {
			public void onSubmit() {
			
			}
		}.setDefaultFormProcessing(false); 
		
		
		
		personId  = new HiddenField("personId",new PropertyModel<>(person, "id"));
		firstName  = new TextField("firstName",new PropertyModel<>(person, "firstName"));
		lastName = new TextField("lastName",new PropertyModel<>(person, "lastName"));
		add(personId);
		add(firstName);
		add(lastName);
		personMobileNumberEditor= new ListEditor<ListItem<PersonMobileNumber>>("personMobileNumbers",personMobileNumbers) {
			@Override
			protected void onPopulateItem(ListItem<ListItem<PersonMobileNumber>> item) {
			}
		};
		personEmailEditor= new ListEditor<ListItem<PersonEmail>>("personEmails",personEmails) {
			@Override
			protected void onPopulateItem(ListItem<ListItem<PersonEmail>> item) {
			}
		};
		personAddressEditor= new ListEditor<ListItem<PersonAddress>>("personAddresses",personAddresses) {
			@Override
			protected void onPopulateItem(ListItem<ListItem<PersonAddress>> item) {
			}
		};
		personBankAccountEditor= new ListEditor<ListItem<PersonBankAccount>>("personBankAccounts",personBankAccounts) {
			@Override
			protected void onPopulateItem(ListItem<ListItem<PersonBankAccount>> item) {
			}
		};
		
		for(PersonMobileNumber pmn:person.getPersonMobileNumbers()) {
			ListItem<PersonMobileNumber> li = new ListItem<>(String.valueOf(actionCounts),actionCounts,new CompoundPropertyModel<>(pmn));
			personMobileNumberEditor.addItem(li);
			actionCounts++;
		}

		for(PersonEmail pe:person.getPersonEmails()) {
			ListItem<PersonEmail> li = new ListItem<>(String.valueOf(actionCounts),actionCounts,new CompoundPropertyModel<>(pe));
			personEmailEditor.addItem(li);
			actionCounts++;
		}
		
		for(PersonAddress pa:person.getPersonAddresses()) {
			ListItem<PersonAddress> li = new ListItem<>(String.valueOf(actionCounts),actionCounts,new CompoundPropertyModel<>(pa));
			personAddressEditor.addItem(li);
			actionCounts++;
		}
		
		for(PersonBankAccount pba:person.getPersonBankAccounts()) {
			ListItem<PersonBankAccount> li = new ListItem<>(String.valueOf(actionCounts),actionCounts,new CompoundPropertyModel<>(pba));
			personBankAccountEditor.addItem(li);
			actionCounts++;
		}
		
		add(personMobileNumberEditor);
		add(personEmailEditor);
		add(personAddressEditor);
		add(personBankAccountEditor);
		
		add(new Button("addMobileNumber")
        {
            public void onSubmit()
            {
            	List<ListItem<PersonMobileNumber>> list = (List)personMobileNumberEditor.getDefaultModelObject();
            	Integer componentId = list.size();
				personMobileNumberEditor.addItem(new ListItem<PersonMobileNumber>(String.valueOf(actionCounts),componentId,new CompoundPropertyModel<>(new PersonMobileNumber())));
				actionCounts++;
            }
        }.setDefaultFormProcessing(false));
		
		add(new Button("addEmail")
        {
            public void onSubmit()
            {
            	List<ListItem<PersonEmail>> list = (List)personEmailEditor.getDefaultModelObject();
            	Integer componentId = list.size();
				personEmailEditor.addItem(new ListItem<PersonEmail>(String.valueOf(actionCounts),componentId,new CompoundPropertyModel<>(new PersonEmail())));
				actionCounts++;
            }
        }.setDefaultFormProcessing(false));
		
		add(new Button("addAddress")
        {
            public void onSubmit()
            {
            	List<ListItem<PersonAddress>> list = (List)personAddressEditor.getDefaultModelObject();
            	Integer componentId = list.size();
            	personAddressEditor.addItem(new ListItem<PersonAddress>(String.valueOf(actionCounts),componentId,new CompoundPropertyModel<>(new PersonAddress())));
            	actionCounts++;
            }
        }.setDefaultFormProcessing(false));
		
		
		add(new Button("addBankAccount")
        {
            public void onSubmit()
            {
            	List<ListItem<PersonBankAccount>> list = (List)personBankAccountEditor.getDefaultModelObject();
            	Integer componentId = list.size();
            	personBankAccountEditor.addItem(new ListItem<PersonBankAccount>(String.valueOf(actionCounts),componentId,new CompoundPropertyModel<>(new PersonBankAccount())));
            	actionCounts++;
            }
        }.setDefaultFormProcessing(false));
		
	}

	@Override
	protected void onSubmit() {
		PersonService ps = new PersonService();
		Person person = new Person();
		if(personId.getInput()!=null && personId.getInput().trim().length()>0) {
			ps.openSession();
			person = ps.findById(Long.parseLong(personId.getInput()));
			person.removeAllCollections();
			ps.merge(person);
			ps.closeSession();
			ps.openSession();
			person = ps.findById(Long.parseLong(personId.getInput()));
			person.setFirstName(firstName.getInput());
			person.setLastName(lastName.getInput());
			personMobileNumbers = (ArrayList<ListItem<PersonMobileNumber>>)personMobileNumberEditor.getDefaultModelObject();
			for(ListItem<PersonMobileNumber> item:personMobileNumbers) {
				PersonMobileNumber pmn = item.getModelObject();
				pmn.setId(null);
				person.addPersonMobileNumber(pmn);
			}
			personEmails = (ArrayList<ListItem<PersonEmail>>)personEmailEditor.getDefaultModelObject();
			for(ListItem<PersonEmail> item:personEmails) {
				PersonEmail pe = item.getModelObject();
				pe.setId(null);
				person.addPersonEmail(pe);
			}
			personAddresses = (ArrayList<ListItem<PersonAddress>>)personAddressEditor.getDefaultModelObject();
			for(ListItem<PersonAddress> item:personAddresses) {
				PersonAddress pa = item.getModelObject();
				pa.setId(null);
				person.addPersonAddress(pa);
			}
			personBankAccounts = (ArrayList<ListItem<PersonBankAccount>>) personBankAccountEditor.getDefaultModelObject();
			for(ListItem<PersonBankAccount> item:personBankAccounts) {
				PersonBankAccount pba = item.getModelObject();
				pba.setId(null);
				person.addPersonBankAccount(pba);
			}
			ps.update(person);
			ps.closeSession();
		
		}else {
			person.setFirstName(firstName.getInput());
			person.setLastName(lastName.getInput());
			personMobileNumbers = (ArrayList<ListItem<PersonMobileNumber>>)personMobileNumberEditor.getDefaultModelObject();
			for(ListItem<PersonMobileNumber> item:personMobileNumbers) {
				PersonMobileNumber pmn = item.getModelObject();
				person.addPersonMobileNumber(pmn);
			}
			personEmails = (ArrayList<ListItem<PersonEmail>>)personEmailEditor.getDefaultModelObject();
			for(ListItem<PersonEmail> item:personEmails) {
				PersonEmail pe = item.getModelObject();
				person.addPersonEmail(pe);
			}
			personAddresses = (ArrayList<ListItem<PersonAddress>>)personAddressEditor.getDefaultModelObject();
			for(ListItem<PersonAddress> item:personAddresses) {
				PersonAddress pa = item.getModelObject();
				person.addPersonAddress(pa);
			}
			personBankAccounts = (ArrayList<ListItem<PersonBankAccount>>) personBankAccountEditor.getDefaultModelObject();
			for(ListItem<PersonBankAccount> item:personBankAccounts) {
				PersonBankAccount pba = item.getModelObject();
				person.addPersonBankAccount(pba);
			}
			ps.openSession();
			ps.persist(person);
			ps.closeSession();
		}
		setResponsePage(BasicCrudPage.class);
		super.onSubmit();
	}

}
