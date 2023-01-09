package com.gel.wicket_training;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.entities.PersonAddress;
import com.gel.wicket_training.entities.PersonBankAccount;
import com.gel.wicket_training.entities.PersonEmail;
import com.gel.wicket_training.entities.PersonMobileNumber;


public class DataTablePage extends IndexPage
{
	public DataTablePage()
	{
		List<IColumn<Person, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<Person, String>(new ResourceModel("id"), "id")
		{
			@Override
			public String getCssClass()
			{
				return "numeric";
			}
		});
		columns.add(new LambdaColumn<>(new ResourceModel("firstName"), "firstName", Person::getFirstName));
		columns.add(new LambdaColumn<Person, String>(new ResourceModel("lastName"), "lastName", Person::getLastName)
		{
			@Override
			public String getCssClass()
			{
				return "last-name";
			}
		});
		
		columns.add(new PropertyColumn<>(new ResourceModel("personMobileNumbers"), "personMobileNumbers") {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void populateItem(Item item, String componentId, IModel model) {
	            item.add(new PersonMobileNumbersCellPanel(componentId, model));
	        }
	    });
		
		columns.add(new PropertyColumn<>(new ResourceModel("personEmails"), "personEmails") {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void populateItem(Item item, String componentId, IModel model) {
	            item.add(new PersonEmailsCellPanel(componentId, model));
	        }
	    });
		
		columns.add(new PropertyColumn<>(new ResourceModel("personAddresses"), "personAddresses") {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void populateItem(Item item, String componentId, IModel model) {
	            item.add(new PersonAdderessesCellPanel(componentId, model));
	        }
	    });
		
		columns.add(new PropertyColumn<>(new ResourceModel("personBankAccounts"), "personBankAccounts") {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void populateItem(Item item, String componentId, IModel model) {
	            item.add(new PersonBankAccountsCellPanel(componentId, model));
	        }
	    });
		columns.add(new AbstractColumn<Person, String>(new ResourceModel("actions"))
		{
			@Override
			public void populateItem(Item<ICellPopulator<Person>> cellItem, String componentId,
				IModel<Person> model)
			{
				cellItem.add(new ActionPanel(componentId, model));
			}
		});
		SortablePersonDataProvider dataProvider = new SortablePersonDataProvider(1,10);
		//DataTable<Person, String> dataTable = new DefaultDataTable<>("table", columns, dataProvider, 5);
		AjaxFallbackDefaultDataTable ajaxDataTable = new AjaxFallbackDefaultDataTable<>("table", columns, dataProvider, 5);
		FilterForm<PersonFilter> personFilter = new FilterForm<>("personFilter", dataProvider);
		
		personFilter.add(new TextField<>("firstName",PropertyModel.of(dataProvider, "filterState.firstName")));
		personFilter.add(new TextField<>("lastName",PropertyModel.of(dataProvider, "filterState.lastName")));
		
		add(personFilter);
		FilterToolbar filterToolBar = new FilterToolbar(ajaxDataTable,personFilter);
		ajaxDataTable.addTopToolbar(filterToolBar);
		ajaxDataTable.setOutputMarkupId(true);
		personFilter.add(ajaxDataTable);
		
	}
	
	class PersonMobileNumbersCellPanel extends Panel {
	    PersonMobileNumbersCellPanel(String componentId, final IModel<Person>rowModel) {
	        super(componentId, rowModel);
	        List<PersonMobileNumber> pmns = rowModel.getObject().getPersonMobileNumbers().stream().collect(Collectors.toList());
	        add(new PersonMobileNumberList("mobileNumbers",pmns));
	    }
	}
	class PersonEmailsCellPanel extends Panel {
		PersonEmailsCellPanel(String componentId, final IModel<Person>rowModel) {
	        super(componentId, rowModel);
	        List<PersonEmail> pes = rowModel.getObject().getPersonEmails().stream().collect(Collectors.toList());
	        add(new PersonEmailList("emails",pes));
	    }
	}
	class PersonAdderessesCellPanel extends Panel {
		PersonAdderessesCellPanel(String componentId, final IModel<Person>rowModel) {
	        super(componentId, rowModel);
	        List<PersonAddress> pas = rowModel.getObject().getPersonAddresses().stream().collect(Collectors.toList());
	        add(new PersonAddressList("addresses",pas));
	    }
	}
	class PersonBankAccountsCellPanel extends Panel {
		PersonBankAccountsCellPanel(String componentId, final IModel<Person>rowModel) {
	        super(componentId, rowModel);
	        List<PersonBankAccount> pbas = rowModel.getObject().getPersonBankAccounts().stream().collect(Collectors.toList());
	        add(new PersonBankAccountList("bankAccounts",pbas));
	    }
	}
}
