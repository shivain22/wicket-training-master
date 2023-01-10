package com.gel.wicket_training.spring_boot.customcomponent;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

public class CustomInputLiComponent<T> extends RepeatingView {

	public CustomInputLiComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public void addItem(T value) {
		ListItem<T> item = (ListItem<T>)value;
		item.add(new Label("lblTxtBox", "Enter Name"));
		item.add(new TextField("txtBox", Model.of(true)));
		item.add(new Label("lblCheckBox", "Wicket"));
		item.add(new CheckBox("checkBox", Model.of(true)));
		item.add(new Label("lblRadioButton", "Test Radio"));
		item.add(new RadioChoice("radioButton", Model.of(false)));
		add(item);
	}

}
