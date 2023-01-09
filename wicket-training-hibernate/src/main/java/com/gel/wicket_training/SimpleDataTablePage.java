package com.gel.wicket_training;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;

import com.gel.wicket_training.entities.Person;

public class SimpleDataTablePage extends IndexPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3636375222086310772L;

	public SimpleDataTablePage() {
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
		SortablePersonDataProvider dataProvider = new SortablePersonDataProvider();
		DefaultDataTable table = new DefaultDataTable("simpleDataTable", columns,dataProvider, 10);
		add(table);
	}
}
