package com.gel.wicket_training;

import org.apache.wicket.model.LoadableDetachableModel;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.service.PersonService;

public class DetachablePersonModel extends LoadableDetachableModel<Person>
{
	private final long id;
	PersonService personService= new PersonService();
	public DetachablePersonModel(Person c)
	{
		this(c.getId());
	}
	public DetachablePersonModel(long id)
	{
		if (id == 0)
		{
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		return Long.valueOf(id).hashCode();
	}
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		else if (obj == null)
		{
			return false;
		}
		else if (obj instanceof DetachablePersonModel)
		{
			DetachablePersonModel other = (DetachablePersonModel)obj;
			return other.id == id;
		}
		return false;
	}

	@Override
	protected Person load()
	{
		personService.openSession();
		Person person = personService.findById(id);
		personService.closeSession();
		return person;
	}
}
