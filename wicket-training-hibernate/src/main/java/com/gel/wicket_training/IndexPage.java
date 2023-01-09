package com.gel.wicket_training;
import java.util.List;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.service.PersonService;

public  class IndexPage extends WebPage {
	private Person selected;
	public IndexPage() {
		add(new BookmarkablePageLink("basicCrudPage", BasicCrudPage.class));
		add(new BookmarkablePageLink("ajaxPage", AjaxPage.class));
		add(new BookmarkablePageLink("ajaxFormInTabbedPanel", AjaxFormInTabbedPanel.class));
		add(new BookmarkablePageLink("dataTablePage", DataTablePage.class));
		add(new BookmarkablePageLink("simpleDataTablePage", SimpleDataTablePage.class));
		add(new BookmarkablePageLink("dataViewPanelPage", DataViewPanelPage.class));
		add(new Label("footer", "This is the footer"));
		add(new Label("hello","Apache Wicket Training"));
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
	    super.renderHead(response);

	    response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings()
	        .getJQueryReference()));  
	}
	
	class ActionPanel extends Panel
	{
		public ActionPanel(String id, IModel<Person> model)
		{
			super(id, model);
			add(new NormalEditLink("editPerson", getSelected())
			{
				@Override
				public void onClick()
				{
					selected = (Person)getParent().getDefaultModelObject();
					PageParameters pp = new PageParameters();
					pp.add("person_id", selected.getId());
					setResponsePage(BasicCrudPage.class,pp);
				}
			});
			add(new NormalDeleteLink("deletePerson", getSelected())
			{
				@Override
				public void onClick()
				{
					selected = (Person)getParent().getDefaultModelObject();
					PersonService ps = new PersonService();
					ps.openSession();
					person = ps.findById(selected.getId());
					ps.delete(person);
					ps.closeSession();
				}
			});
		}
	}
	public Person getSelected()
	{
		return selected;
	}

	public void setSelected(Person selected)
	{
		addStateChange();
		this.selected = selected;
	}
	
}