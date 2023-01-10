package com.gel.wicket_training.spring_boot;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class LevelOneNestingExample extends AjaxPage {
	public LevelOneNestingExample() {
		add(new BookmarkablePageLink("deepNestedPageExample", DeepNestedPageExample.class));
		add(new Label("lblLevelOneNestingExample", "LevelOneNestingExample page's contents..."));
	}
}
