package main.services;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import main.models.Person;


@Stateful(name = "connectedUser")
@RunAs("user")
public class LoginService implements ILoginService {

	@EJB
	IPersonService ps;
	
	Person admin = new Person("FIRSTNAME", "LASTNAME", "name@domain.com", "www.domain.com", null, "VALID_PASS");
	
	public void setPersonService(IPersonService service) {
		ps = service;
	}

	public Person login(String email, String password) {
//		if(email.equals("admin@admin.com") && password.equals("admin"))
//			return admin;
		Person person = ps.getPersonByEmail(email);
		if (person != null && password.equals(person.getPassword())) {
			return person;
		}
		return null;
	}

}
