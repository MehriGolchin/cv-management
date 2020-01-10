package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import main.models.Activity;
import main.models.Person;
import main.services.IActivityService;
import main.services.IPersonService;

/*
 * In this unit test we just test create and update 
 * because the other methods just call the entity manager methods
 * only the methods with a business logic will be verified
 */

public class ActivityTest {

	@EJB
	IPersonService ps;
	
	@EJB
	IActivityService as;

	@EJB(name = "ActivityBean")
	Caller user;
	
	private EJBContainer _container;

	@Before
	public void setUp() throws Exception {
		_container = EJBContainer.createEJBContainer();
		_container.getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		_container.close();
	}

	@Test
	public void testInjectEJB() {
		assertNotNull(as);
	}

	@Test
	public void testActivity_create_new_activity() throws Exception {
		// run under connected user
		user.call(new Callable<Object>() {
			public Object call() throws Exception {
				// arrange
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
				Person p = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", date, "VALID_PASS");

				// act
				Person result = ps.createPerson(p);
				
				// arrange
				Activity a = new Activity(result.getPersonId(), "2019", "expérience_professionnelle", "TEST_TITLE", "TEST_DESCRIPTION", "www.domain.com");

				// act
				as.createActivity(a);

				// assert
				Activity actual = as.getActivityById((long) 1);
				assertTrue(new ReflectionEquals(a).matches(actual));
				return null;
			}
		});
	}

	@Test
	public void testActivity_update_existing_activity() throws Exception {
		// run under connected user
		user.call(new Callable<Object>() {
			public Object call() throws Exception {
				// arrange
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
				Person p = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", date, "VALID_PASS");

				// act
				Person result = ps.createPerson(p);
				
				// arrange
				Activity a = new Activity();
				a.setActivityId((long) 1);
				a.setPersonId(result.getPersonId());
				a.setDescription("TEST_DESCRIPTION");
				a.setNature("expérience_professionnelle");
				a.setTitle("TEST_TITLE");
				a.setWebSite("www.site.com");
				a.setYear("2019");

				// act
				as.createActivity(a);

				// assert
				Activity actual = as.getActivityById((long) 1);
				assertTrue(new ReflectionEquals(a).matches(actual));
				return null;
			}
		});
	}

	@Test
	public void testActivity_find_activities_by_title() throws Exception {
		// run under connected user
		user.call(new Callable<Person>() {
			public Person call() throws Exception {
				// arrange
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
				Person p = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", date, "VALID_PASS");

				// act
				Person result = ps.createPerson(p);
				
				// arrange
				Activity a1 = new Activity();
				a1.setPersonId(result.getPersonId());
				a1.setDescription("TEST_DESCRIPTION");
				a1.setNature("expérience_professionnelle");
				a1.setTitle("Web developer");
				a1.setWebSite("www.site.com");
				a1.setYear("2019");

				Activity a2 = new Activity();
				a2.setPersonId(result.getPersonId());
				a2.setDescription("TEST_DESCRIPTION");
				a2.setNature("expérience_professionnelle");
				a2.setTitle("BI analyst");
				a2.setWebSite("www.site.com");
				a2.setYear("2019");

				Activity a3 = new Activity();
				a3.setPersonId(result.getPersonId());
				a3.setDescription("TEST_DESCRIPTION");
				a3.setNature("expérience_professionnelle");
				a3.setTitle("application developer");
				a3.setWebSite("www.site.com");
				a3.setYear("2019");

				// act
				as.createActivity(a1);
				as.createActivity(a2);
				as.createActivity(a3);
				return null;
			}
		});

		// assert
		List<Activity> activities = as.findActivitiesByTitle("developer");
		assertEquals(activities.size(), 2);
	}
	
	@Test
	public void testActivity_unauthenticated_user_to_create_activity() throws Exception {
		try {
			// arrange
			Activity a = new Activity();
			a.setDescription("TEST_DESCRIPTION");
			a.setNature("expérience_professionnelle");
			a.setTitle("TEST_TITLE");
			a.setWebSite("www.site.com");
			a.setYear("2019");

			// act
			as.createActivity(a);

			// assert
			Activity actual = as.getActivityById((long) 1);
			assertTrue(new ReflectionEquals(a).matches(actual));
            fail("Unauthenticated users should not be able to create activity");
        } catch (EJBAccessException e) {
            // Good, guests cannot add things
        }
	}

	public static interface Caller {
		public <V> V call(Callable<V> callable) throws Exception;
	}

	@Stateless
	@RunAs("user")
	public static class ActivityBean implements Caller {
		public <V> V call(Callable<V> callable) throws Exception {
			return callable.call();
		}
	}

}
