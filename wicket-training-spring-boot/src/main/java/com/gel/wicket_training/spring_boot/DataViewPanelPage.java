package com.gel.wicket_training.spring_boot;


import org.apache.wicket.markup.html.WebPage;

public class DataViewPanelPage extends WebPage {

	public DataViewPanelPage() {
		DataViewPanel dataViewPanel = new DataViewPanel("dataViewPanel");
		add(dataViewPanel);
	}
}
