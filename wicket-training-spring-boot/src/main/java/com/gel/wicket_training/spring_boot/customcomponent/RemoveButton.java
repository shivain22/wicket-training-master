package com.gel.wicket_training.spring_boot.customcomponent;

import com.gel.wicket_training.spring_boot.AddEditForm;
import org.apache.wicket.markup.html.list.ListItem;

import java.util.Iterator;

public class RemoveButton extends EditorButton
{

    public RemoveButton(String id)
    {
        super(id);
        setDefaultFormProcessing(false);
    }

    @Override
    public void onSubmit()
    {
    	ListItem<?> parent = findParent(ListItem.class);
    	AddEditForm addForm = findParent(AddEditForm.class);
    	
    	ListEditor< ? > editor = (ListEditor<?>)parent.getParent();
    	Iterator it1 = editor.items.iterator();
    	
        int idx = getItem().getIndex();
        int i=0;
        System.out.println("Index of removing item="+idx);
    	while(it1.hasNext()) {
    		ListItem li= (ListItem) it1.next();
    		if(li.getIndex()==idx) {
    			System.out.println("Editor item removed -"+i);
    			it1.remove();
    		}
    		i++;
    	}
        Iterator it = getItem().getParent().iterator();
        while(it.hasNext()) {
        	ListItem< ? > item = (ListItem< ? >)it.next();
        	if(item.getIndex()==idx) {
        		it.remove();
        		System.out.println("List item removed - "+idx);
        		int removeAction = addForm.getActionCounts();
        		addForm.setActionCounts(removeAction+1);
        		
        	}
        }
        
    }
}
