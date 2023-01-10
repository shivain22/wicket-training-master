package com.gel.wicket_training.spring_boot;


import org.apache.wicket.markup.html.basic.Label;

public class DeepNestedPageExample extends LevelOneNestingExample {
	public DeepNestedPageExample() {
		add(new Label("lblDeepNestedPageExample", "This is in the subclass of LevelOneNestingExample"));
	}
}
