package com.gel.wicket_training.spring_boot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;


public class SortablePersonDataProvider extends SortableDataProvider<Person, String> implements IFilterStateLocator<PersonFilter>
{
	private static final long serialVersionUID = 507426745396537534L;
	private PersonFilter personFilter = new PersonFilter();
    @SpringBean
    IPersonRepository personRepository;
	List<Person> persons = new ArrayList<>();
	int first=1;
	int count=10;
	int totalRows=0;
	public SortablePersonDataProvider()
	{
		Injector.get().inject(this);
		setSort("firstName", SortOrder.ASCENDING);
		this.totalRows =(int) personRepository.count();
	}
	public SortablePersonDataProvider(int first, int count)
	{
		Injector.get().inject(this);
		this.first=first;
		this.count=count;
		setSort("firstName", SortOrder.ASCENDING);
		this.totalRows = (int)personRepository.count();
	}

	@Override
	public Iterator<Person> iterator(long first, long count)
	{
		this.first=(int)first;
		this.count=(int)count;
		this.persons = null;// personService.findAll(getSort(),(int)first,(int)count,personFilter);
		return persons.iterator();
	}

	@Override
	public long size()
	{
		Long size =0l;// personService.countAll(getSort(),first,count,personFilter);
		return size;
	}

	@Override
	public IModel<Person> model(Person object)
	{
		return new DetachablePersonModel(object);
	}

	@Override
	public PersonFilter getFilterState()
	{
	    return personFilter;
	}

	@Override
	public void setFilterState(PersonFilter state)
	{
	    personFilter  = state;
	}
}
