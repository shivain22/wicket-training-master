package com.gel.wicket_training.spring_boot;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class AjaxFormInTabbedPanel extends HomePage
{
	
	
	List<ITab> tabs = new ArrayList<>();
	AjaxTabbedPanel tabbedPanel  = new AjaxTabbedPanel<>("tabs", tabs);
	/**
	 * Constructor
	 */
	public AjaxFormInTabbedPanel()
	{
		
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

		add(tabbedPanel);
	}

	private class PersonDetail extends Panel
	{
		public PersonDetail(String id)
		{
			super(id);
			TextField firstName = new TextField<>("txtFirstName",Model.of(""));
			TextField lastName = new TextField<>("txtLastName",Model.of(""));
			Form tabAddForm = new Form<>("tabPersonDetailsAddForm");
			tabAddForm.add(new Label("lblFirstName",Model.of("First Name")));
			tabAddForm.add(new Label("lblLastName",Model.of("Last Name")));
			tabAddForm.add(firstName);
			tabAddForm.add(lastName);
			tabAddForm.add(new Button("btnGoToMobileNumber") {

				@Override
				public void onSubmit() {
					System.out.println(firstName.getInput()+"===="+lastName.getInput());
					tabbedPanel.setSelectedTab(1);
					super.onSubmit();
				}
				
				
			}.setDefaultFormProcessing(false));
			add(tabAddForm);
		}
	}

	private  class PersonMobileNumber extends Panel
	{
		public PersonMobileNumber(String id)
		{
			super(id);
			TextField mobileNumber = new TextField<>("txtMobileNumber",Model.of(""));
			Form tabMobileNumberAddForm = new Form<>("tabMobileNumberAddForm");
			tabMobileNumberAddForm.add(new Label("lblMobileNumber",Model.of("Mobile Number")));
			tabMobileNumberAddForm.add(mobileNumber);
			tabMobileNumberAddForm.add(new Button("btnGoToEmail") {

				@Override
				public void onSubmit() {
					System.out.println(mobileNumber.getInput());
					tabbedPanel.setSelectedTab(2);
					super.onSubmit();
				}
				
				
			}.setDefaultFormProcessing(false));
			tabMobileNumberAddForm.add(new Button("btnGoToPersonDetail") {

				@Override
				public void onSubmit() {
					System.out.println(mobileNumber.getInput());
					tabbedPanel.setSelectedTab(0);
					super.onSubmit();
				}
				
				
			}.setDefaultFormProcessing(false));
			add(tabMobileNumberAddForm);
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
