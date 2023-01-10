package com.gel.wicket_training.spring_boot;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gel.wicket_training.spring_boot.entities.Person;
import com.gel.wicket_training.spring_boot.repositories.IPersonRepository;

public class DetachablePersonModel extends LoadableDetachableModel<Person>
{
	private final long id;
    @SpringBean
	IPersonRepository personRepository;
	public DetachablePersonModel(Person c)
	{
		this(c.getId());
		Injector.get().inject(this);
	}
	public DetachablePersonModel(long id)
	{
		if (id == 0)
		{
			throw new IllegalArgumentException();
		}
		this.id = id;
		Injector.get().inject(this);
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

		Person person = personRepository.findById(id).get();

		return person;
	}
}
