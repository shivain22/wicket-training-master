package com.gel.wicket_training.spring_boot;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@WicketHomePage
public class HomePage extends WebPage {
	public HomePage() {
		Form form = new Form<Void>("form"){
			@Override
			protected void onSubmit() {
					setResponsePage(SecondPage.class);
			}
		};
		queue(form);
		add(new Link<Void>("mylink") {
					@Override
					public void onClick() {
						setResponsePage(SecondPage.class);
					}
        });
	}
}
