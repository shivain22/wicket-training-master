package com.gel.wicket_training.spring_boot;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;


@WicketHomePage
public  class IndexPage extends WebPage {

    

	public IndexPage() {
		add(new BookmarkablePageLink("homePage", HomePage.class));
		add(new Label("footer", "This is the footer"));
		add(new Label("hello","Apache Wicket Training"));
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
	    super.renderHead(response);

	    response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings()
	        .getJQueryReference()));  
	}
	

	
}
