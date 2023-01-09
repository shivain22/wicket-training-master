package com.gel.wicket_training;

public class DataViewPanelPage extends IndexPage {

	public DataViewPanelPage() {
		DataViewPanel dataViewPanel = new DataViewPanel("dataViewPanel");
		add(dataViewPanel);
	}
}
