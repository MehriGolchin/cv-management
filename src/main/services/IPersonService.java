package main.services;

import java.util.List;
import javax.ejb.Local;
import main.models.Person;

@Local
public interface IPersonService {

	List<Person> getPersons();
	Person createPerson(Person person);
	Person getPersonById(Long personId);
	Person getPersonByEmail(String email);
	void deletePerson(Person person);
	List<Person> findPersonsByFirstName(String firstname);
	List<Person> findPersonsByLastName(String lastname);
	List<Person> findPersonByActivityTitle(String activity);
}
