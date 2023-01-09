package com.gel.wicket_training;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

import com.gel.wicket_training.entities.Person;

public class DataViewPanel extends Panel {

  public DataViewPanel(String id) {
      super(id);
      SortablePersonDataProvider spdp = new SortablePersonDataProvider();
      DataView dataView = new DataView<Person>("dataView", spdp) {
		@Override
		protected void populateItem(Item<Person> item) {
			Person person = item.getModelObject();
			item.add(new Label("firstLastName", person.getFirstName() + " " +person.getLastName()));
		}
	};
      dataView.setItemsPerPage(5);
      add(dataView);

      CustomPagingNavigator customPagingNavigator = new CustomPagingNavigator("paginator", dataView);
      add(customPagingNavigator);
  }
}