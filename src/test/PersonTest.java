package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.models.Activity;
import main.models.Person;
import main.services.IActivityService;
import main.services.IPersonService;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

	private Date theBirthdate;

	@EJB
	private IPersonService ps;

	@EJB
	private IActivityService as;

	@EJB(name = "UserBean")
	private Caller user;

	private EJBContainer _container;

	@BeforeEach
	public void setUp() throws Exception {
		_container = EJBContainer.createEJBContainer();
		_container.getContext().bind("inject", this);

		theBirthdate = new SimpleDateFormat("dd/MM/yyyy").parse("12/06/1995");
	}

	@AfterEach
	public void tearDown() {
		_container.close();
	}

	@Test
	public void testInjectEJB() {
		assertNotNull(ps);
	}

	@Test
	public void testPerson_create_person() throws Exception {
		// arrange
		Person p = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", theBirthdate, "VALID_PASS");

		// act
		user.call(new Callable<Person>() {
			public Person call() throws Exception {
				return ps.createPerson(p);
			}
		});

		// assert
		assertNotNull(ps.getPersonByEmail("name@domain.com"));
	}

	@Test
	public void testPerson_find_persons_by_firstName() throws Exception {
		// arrange
		user.call(new Callable<Person>() {
			public Person call() throws Exception {
				// arrange
				Person p1 = new Person("Emma", "Gerard", "emma@domain.com", "emma.com", theBirthdate, "VALID_PASS");
				Person p2 = new Person("Remi", "Dubois", "remi@domain.com", "remi.com", theBirthdate, "VALID_PASS");
				Person p3 = new Person("Lila", "Vitton", "lila@domain.com", "lila.com", theBirthdate, "VALID_PASS");

				ps.createPerson(p1);
				ps.createPerson(p2);
				return ps.createPerson(p3);
			}
		});

		// act
		List<Person> persons = ps.findPersonsByFirstName("em");

		// assert
		assertEquals(persons.size(), 2);
	}

	@Test
	public void testPerson_update_person() throws Exception {
		user.call(new Callable<Person>() {
			public Person call() throws Exception {
				// arrange
				Person person = new Person("Emma", "Gerard", "emma@domain.com", "emma.com", theBirthdate, "VALID_PASS");
				ps.createPerson(person);

				//act
				person.setFirstName("NEW_FIRSTNAME");
				ps.createPerson(person);

				// assert
				assertNotNull(ps.findPersonsByFirstName("NEW_FIRSTNAME"));
				return null;
			}
		});
	}

	// For LastName is the same
	@Test
	public void testPerson_unauthenticated_user() throws Exception {
		// arrange
		Person p = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", theBirthdate, "VALID_PASS");

		// act & assert
		assertThrows(EJBAccessException.class, () -> ps.createPerson(p));
	}

	@Test
	public void testPerson_find_persons_by_activity() throws Exception {
		user.call(new Callable<Object>() {
			public Object call() throws Exception {
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse("12/06/1995");
				Person p = new Person("Emma", "Gerard", "emma@domain.com", "emma.com", date, "VALID_PASS");

				Person result = ps.createPerson(p);

				Activity a = new Activity(result.getPersonId(), "2019", "formation", "web developer", "desc",
						"www");
				Activity a1 = new Activity(result.getPersonId(), "2019", "formation", "developement web", "desc",
						"www");

				as.createActivity(a);
				as.createActivity(a1);
				
				return null;
			}
		});
		
		List<Person> persons = ps.findPersonByActivityTitle("web");
		assertEquals(persons.size(), 1);
	}

	public static interface Caller {
		public <V> V call(Callable<V> callable) throws Exception;
	}

	@Stateless
	@RunAs("user")
	public static class UserBean implements Caller {
		public <V> V call(Callable<V> callable) throws Exception {
			return callable.call();
		}
	}

}
