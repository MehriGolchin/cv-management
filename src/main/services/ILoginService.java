package main.services;

import javax.ejb.Local;
import main.models.Person;

@Local
public interface ILoginService {

	void setPersonService(IPersonService service);
	Person login(String email, String password);
	
}
