package main.services;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import main.models.Person;

@Stateless
public class PersonService implements IPersonService {

	@PersistenceContext(unitName = "cvmPersistence")
	private EntityManager em;

	@PermitAll
	public List<Person> getPersons() {
		return em.createQuery("Select p From Person p", Person.class)
				.setMaxResults(100)
				.getResultList();
	}

	//@RolesAllowed({"user"})
	public Person createPerson(Person person) {
		if (person.getPersonId() == null) // create
		{
			em.persist(person);
		} else {
			person = em.merge(person); // update
		}
		return person;
	}

	@Override
	public Person getPersonById(Long personId) {
		TypedQuery<Person> query = em.createQuery("From Person where personId=:personId", Person.class);
		query.setParameter("personId", personId);
		return query.getSingleResult();
	}

	@PermitAll
	public Person getPersonByEmail(String email) {
		try {
			TypedQuery<Person> query = em.createQuery("From Person where email=:email", Person.class);
			query.setParameter("email", email);
			return query.getSingleResult();	
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

	//@RolesAllowed({"user"})
	public void deletePerson(Person person) {
		person = em.merge(person);
		em.remove(person);
	}
	
	@PermitAll
	public List<Person> findPersonsByFirstName(String firstName) {
		TypedQuery<Person> query = em.createQuery("From Person where lower(firstName) Like :firstName", Person.class);
		query.setParameter("firstName", '%' + firstName.toLowerCase() + '%');
		return query.setMaxResults(100).getResultList();
	}

	@PermitAll
	public List<Person> findPersonsByLastName(String lastName) {
		TypedQuery<Person> query = em.createQuery("From Person where lower(lastName) Like :lastName", Person.class);
		query.setParameter("lastName", '%' + lastName.toLowerCase() + '%');
		return query.setMaxResults(100).getResultList();
	}

	@PermitAll
	public List<Person> findPersonByActivityTitle(String activity) {
		TypedQuery<Person> query = em.createQuery("SELECT Distinct p FROM Person p INNER JOIN p.activities a where lower(a.title) Like :activity", Person.class) ;
		query.setParameter("activity", '%' + activity.toLowerCase() + '%');
		return query.setMaxResults(100).getResultList();
	}

}
