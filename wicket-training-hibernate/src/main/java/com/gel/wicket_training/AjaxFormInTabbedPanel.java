package com.gel.wicket_training;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class AjaxFormInTabbedPanel extends IndexPage
{
	/**
	 * Constructor
	 */
	public AjaxFormInTabbedPanel()
	{
		List<ITab> tabs = new ArrayList<>();
		tabs.add(new AbstractTab(new Model<>("Details"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonDetail(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<>("Mobile Number"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonMobileNumber(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<>("Email"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonEmail(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model<>("Address"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonAddress(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model<>("Bank Account"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonBankAccount(panelId);
			}
		});

		add(new AjaxTabbedPanel<>("tabs", tabs));
	}

	private static class PersonDetail extends Panel
	{
		public PersonDetail(String id)
		{
			super(id);
		}
	}

	private static class PersonMobileNumber extends Panel
	{
		public PersonMobileNumber(String id)
		{
			super(id);
		}
	}

	
	private static class PersonEmail extends Panel
	{
		public PersonEmail(String id)
		{
			super(id);
		}
	}
	
	private static class PersonAddress extends Panel
	{
		public PersonAddress(String id)
		{
			super(id);
		}
	}
	
	private static class PersonBankAccount extends Panel
	{
		public PersonBankAccount(String id)
		{
			super(id);
		}
	}
}
