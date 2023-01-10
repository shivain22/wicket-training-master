package com.gel.wicket_training.spring_boot.repositories;

import com.gel.wicket_training.spring_boot.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IPersonRepository extends JpaRepository<Person,Long>,JpaSpecificationExecutor<Person> {
	Person findByFirstNameAndLastName(String firstName,String lastName);
}
