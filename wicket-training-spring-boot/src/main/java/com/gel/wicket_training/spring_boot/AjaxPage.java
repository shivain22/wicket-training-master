package com.gel.wicket_training.spring_boot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxClientInfoBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.settings.ExceptionSettings;
import org.apache.wicket.settings.RequestCycleSettings;

import com.gel.wicket_training.spring_boot.entities.Person;



public class AjaxPage extends WebPage {
	private int counter1 = 0;
	private int counter2 = 0;
	private int counter3 = 0;
	private String str = "Test";
	
	public int getCounter1()
	{
		return counter1;
	}

	public int getCounter2()
	{
		return counter2;
	}

	public int getCounter3()
	{
		return counter3;
	}
	public AjaxPage() {
		add(new BookmarkablePageLink("levelOneNestingExample", LevelOneNestingExample.class));
		
		final MultiLineLabel clientInfo = new MultiLineLabel("clientinfo", new IModel<String>()
		{
			@Override
			public String getObject()
			{
				ClientProperties properties = getClientProperties();
				return properties.toString();
			}
		});
		clientInfo.setOutputMarkupPlaceholderTag(true);
		clientInfo.setVisible(false);

		IModel<String> clientTimeModel = () -> {
			ClientProperties properties = getClientProperties();
			TimeZone timeZone = properties.getTimeZone();
			if (timeZone != null)
			{
				Calendar cal = Calendar.getInstance(timeZone);
				Locale locale = getLocale();
				DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.LONG, locale);
				dateFormat.setTimeZone(timeZone);
				String calAsString = dateFormat.format(cal.getTime());
				StringBuilder b = new StringBuilder("Based on your settings, your time is: ");
				b.append(calAsString);
				b.append(" (and your time zone is ");
				b.append(timeZone.getDisplayName(getLocale()));
				b.append(')');
				return b.toString();
			}
			return "Unfortunately, we were not able to figure out what your time zone is, so we have"
					+ " no idea what your time is";
		};
		final Label clientTime = new Label("clienttime", clientTimeModel);
		clientTime.setOutputMarkupPlaceholderTag(true);
		clientTime.setVisible(false);

		add(new AjaxClientInfoBehavior() {

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);

				String script = "Wicket.BrowserInfo.collectExtraInfo = function(info) { info.extendedProperty = 'This property was read extra.'; };";

				response.render(JavaScriptHeaderItem.forScript(script, "extended-client-info"));
			}

			@Override
			protected WebClientInfo newWebClientInfo(RequestCycle requestCycle)
			{
				return new WebClientInfo(requestCycle, new ExtendedClientProperties());
			}

			@Override
			protected void onClientInfo(AjaxRequestTarget target, WebClientInfo webClientInfo)
			{
				clientInfo.setVisible(true);
				clientTime.setVisible(true);

				target.add(clientInfo, clientTime);
			}
		});

		add(clientInfo, clientTime);
	
		Person person = new Person();
		person.setFirstName("Siva");
		person.setLastName("Kumar");
		person.setId(1l);
		
		final Label c1 = new Label("c1", new PropertyModel<>(this,"str"));
		c1.setOutputMarkupId(true);
		add(c1);

		final Label c2 = new Label("c2", new PropertyModel<>(this, "counter2"));
		c2.setOutputMarkupId(true);
		add(c2);

		final Label c3 = new Label("c3", new PropertyModel<>(this, "counter3"));
		c3.setOutputMarkupId(true);
		add(c3);

		add(new AjaxLink<Void>("c1-link")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				counter1++;
				person.setFirstName("First Me-"+counter1);
				person.setLastName("Last Me-"+counter1);
				person.setId(Long.valueOf(counter1));
				str = String.valueOf(counter1);
				target.add(c1);
			}
		});

		add(new AjaxFallbackLink<Void>("c2-link")
		{
			@Override
			public void onClick(Optional<AjaxRequestTarget> targetOptional)
			{
				counter2++;
				// notice that for a fallback link we need to make sure the
				// target is not null. if the target is null ajax failed and the
				// fallback was used, so there is no need to do any ajax-related
				// processing.
				targetOptional.ifPresent(target -> target.add(c2));
			}
		});

		add(IndicatingAjaxLink.onClick("c3-link", (link, target) -> {
			counter3++;
			target.add(c3);
			try
			{
				// sleep for 5 seconds to show off the busy indicator
				TimeUnit.SECONDS.sleep(5);
			}
			catch (InterruptedException e)
			{
				// noop
			}
		}));

		add(new AjaxLink<Void>("success-link")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);

				IAjaxCallListener ajaxCallListener = new AjaxCallListener() {
					@Override
					public CharSequence getSuccessHandler(Component component)
					{
						return "alert('Success');";
					}

					@Override
					public CharSequence getBeforeSendHandler(Component component)
					{
						return "alert('Before ajax call');";
					}

					@Override
					public CharSequence getFailureHandler(Component component)
					{
						return "alert('Failure');";
					}
				};
				attributes.getAjaxCallListeners().add(ajaxCallListener);

				List<CharSequence> urlArgumentMethods = attributes.getDynamicExtraParameters();
				urlArgumentMethods.add("return {'htmlname': document.documentElement.tagName};");
				urlArgumentMethods.add("return {'bodyname': document.body.tagName};");
			}
		});

		add(new AjaxLink<Void>("failure-link")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				// Set the proper setting to execute ajax failure handler
				// note: will be set until the "exception" link is clicked or the application is
				// restarted
				getApplication().getExceptionSettings().setAjaxErrorHandlingStrategy(
					ExceptionSettings.AjaxErrorStrategy.INVOKE_FAILURE_HANDLER);

				throw new WicketRuntimeException("Failure link clicked");
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);
				
				IAjaxCallListener ajaxCallListener = new AjaxCallListener() {
					@Override
					public CharSequence getBeforeHandler(Component component)
					{
						return "alert('Before ajax call');";
					}

					@Override
					public CharSequence getSuccessHandler(Component component)
					{
						return "alert('Success');";
					}

					@Override
					public CharSequence getFailureHandler(Component component)
					{
						return "alert('Failure');";
					}
				};
				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}
		});

		add(new AjaxLink<Void>("set-response-page") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AjaxPage.class);
			}
		});

		add(new AjaxLink<Void>("exception") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				// Set the proper setting to show the error page
				// note: will be set until the "failure" link is clicked or the application is
				// restarted
				getApplication().getExceptionSettings().setAjaxErrorHandlingStrategy(
					ExceptionSettings.AjaxErrorStrategy.INVOKE_FAILURE_HANDLER);

				throw new RuntimeException("test whether the exception handling works");
			}
			
			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);
				
				IAjaxCallListener ajaxCallListener = new AjaxCallListener() {
					@Override
					public CharSequence getBeforeHandler(Component component)
					{
						return "alert('Before ajax call');";
					}

					@Override
					public CharSequence getSuccessHandler(Component component)
					{
						return "alert('Success');";
					}

					@Override
					public CharSequence getFailureHandler(Component component)
					{
						return "alert('Failure');";
					}
				};
				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}
		});
	
		
	}
	
	private ClientProperties getClientProperties()
	{
		RequestCycleSettings requestCycleSettings = getApplication().getRequestCycleSettings();
		boolean gatherExtendedBrowserInfo = requestCycleSettings.getGatherExtendedBrowserInfo();
		ClientProperties properties = null;
		try
		{
			requestCycleSettings.setGatherExtendedBrowserInfo(false);
			WebClientInfo clientInfo = (WebClientInfo) getSession().getClientInfo();
			properties = clientInfo.getProperties();
		}
		finally
		{
			requestCycleSettings.setGatherExtendedBrowserInfo(gatherExtendedBrowserInfo);
		}
		return properties;
	}
}
