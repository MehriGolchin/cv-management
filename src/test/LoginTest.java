package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.models.Person;
import main.services.ILoginService;
import main.services.IPersonService;

import static org.mockito.Mockito.*;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

public class LoginTest {
	
	/*
	 * In this unit test we test Login with different valid and invalid
	 * email and password with mockito for mocking person to login
	 */

	Person thePerson = new Person();

	@EJB
	ILoginService loginService;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testLogin_should_return_correct_user() {
		// arrange
		IPersonService personService = mock(IPersonService.class);
		when(personService.getPersonByEmail("name@domain.com")).thenReturn(thePerson);
		thePerson.setEmail("name@domain.com");
		thePerson.setPassword("VALID_PASS");

		loginService.setPersonService(personService);

		// act
		Person actual = loginService.login("name@domain.com", "VALID_PASS");

		// assert
		assertEquals(thePerson, actual);
	}

	@Test
	public void testLogin_should_return_null_when_pass_is_not_valid() {
		// arrange
		IPersonService personService = mock(IPersonService.class);
		when(personService.getPersonByEmail("name@domain.com")).thenReturn(thePerson);
		thePerson.setEmail("name@domain.com");
		thePerson.setPassword("VALID_PASS");

		loginService.setPersonService(personService);

		// act
		Person actual = loginService.login("name@domain.com", "INVALID_PASS");

		// assert
		assertNull(actual);
	}

	@Test
	public void testLogin_should_return_null_when_email_is_not_valid() {
		// arrange
		IPersonService personService = mock(IPersonService.class);
		when(personService.getPersonByEmail("name@domain.com")).thenReturn(thePerson);
		thePerson.setEmail("name@domain.com");
		thePerson.setPassword("VALID_PASS");

		loginService.setPersonService(personService);

		// act
		Person actual = loginService.login("username@domain.com", "VALID_PASS");

		// assert
		assertNull(actual);
	}

}
