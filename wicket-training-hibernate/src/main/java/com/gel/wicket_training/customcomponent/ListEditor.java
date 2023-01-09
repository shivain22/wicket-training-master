package com.gel.wicket_training.customcomponent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.gel.wicket_training.entities.PersonMobileNumber;

public abstract class ListEditor<T> extends RepeatingView 
                             implements IFormModelUpdateListener
{
    List<T> items;
    public ListEditor(String id, IModel<List<T>> model)
    {
        super(id, model);
    }

    public ListEditor(final String id, final List<T> list)
	{
		this(id, Model.ofList(list));
		this.items=list;
	}
    
    
    
    protected abstract void onPopulateItem(ListItem<T> item);

    public void addItem(T value)
    {
        items.add(value);
        visitChildren(ListItem.class,new IVisitor<ListItem, Void>() {
        	int i=0;
			@Override
			public void component(ListItem object, IVisit<Void> visit) {
				object.setIndex(i++);
			}
		});
        
        ListItem<T> item = (ListItem<T>)value;
        Field[] allFields = item.getModelObject().getClass().getDeclaredFields();
        for(Field f:allFields) {
        	if(f.getName().equals("id") && f.getType().equals(Long.class)) {
        		HiddenField<Long> id = new HiddenField<>(f.getName(),new PropertyModel<>(item.getModelObject(), f.getName()));
        		item.add(id);
        	}
        	if(f.getType().equals(String.class)) {
        		Label label = new Label("lbl"+capitalizeFirst(f.getName()),Model.of(humanReadableText(f.getName())+" - "+item.getIndex()));
        		TextField<String> tf = new TextField<String>(f.getName(),new PropertyModel<>(item.getModelObject(), f.getName()));
        		item.add(label);
        		item.add(tf);
        	}        	
        }
        RemoveButton rb = new RemoveButton("remove");
        item.add(rb);
        add(item);
        onPopulateItem(item);
    }

    protected void onBeforeRender()
    {
        if (!hasBeenRendered())
        {
            items = (List<T>)getDefaultModelObject();
            for (int i = 0; i < items.size(); i++)
            {
                //ListItem<T> li = new ListItem<T>(newChildId(), i);
                //add(li);
                //onPopulateItem(li);
            }
        }
        super.onBeforeRender();
    }

    public void updateModel()
    {
        setDefaultModelObject(items);
    }
    
    private String capitalizeFirst(String str) {
    	return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
    }
    
    Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");
    private List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }
    
    private String sentenceCase(List<String> words) {
        List<String> capitalized = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String currentWord = words.get(i);
            if (i == 0) {
                capitalized.add(capitalizeFirst(currentWord));
            } else {
                capitalized.add(currentWord.toLowerCase());
            }
        }
        return String.join(" ", capitalized) + ".";
    }
    
    private String humanReadableText(String text) {
    	return sentenceCase(findWordsInMixedCase(text));
    }
}