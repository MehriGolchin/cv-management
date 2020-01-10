package main.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.bean.ManagedBean;
import main.models.Person;
import main.services.IPersonService;

@ManagedBean(name = "person")
@SessionScoped
public class PersonController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	IPersonService ps;
	
	Person thePerson = new Person();
	
	@PostConstruct
    public void init() {
		System.out.println("Create " + this);
        if (ps.getPersons().size() == 0) {
        	Person p1 = new Person("Emma", "Gerard", "emma@domain.com", "emma.com", null, "123456");
			Person p2 = new Person("Remi", "Dubois", "remi@domain.com", "remi.com", null, "123456");
			Person p3 = new Person("Lila", "Vitton", "lila@domain.com", "lila.com", null, "123456");
            ps.createPerson(p1);
            ps.createPerson(p2);
            ps.createPerson(p3);
        }
	}
	
	private Long personId;
	private String lastName;
	private String firstName;
	private String email;
	private String webSite;
	private Date birthDate;
	private String password;
	
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void toUpper(AjaxBehaviorEvent event) {
		firstName = thePerson.getFirstName().toUpperCase();
	}
	
	public String save() {
		Person newPerson = null;
		newPerson = new Person(firstName, lastName, email.toLowerCase(), webSite, birthDate, password);
        ps.createPerson(newPerson);
        return "persons?faces-redirect=true";
    }
	
	public String update() {
		ps.createPerson(thePerson);
        return "showPerson?faces-redirect=true";
	}
	
	public String newPerson() {
		thePerson = new Person();
        return "editPerson?faces-redirect=true";
    }
	
	public List<Person> getPersons(){
		return ps.getPersons();
	}
	
	public Person getThePerson() {
		return thePerson;
	}
	
	public String edit(Long personId) {
        thePerson = ps.getPersonById(personId);
        return "editPerson?faces-redirect=true";
    } 
	
	public String show(Long personId) {
        thePerson = ps.getPersonById(personId);
        return "showPerson?faces-redirect=true";
    }
	
	public String showCv(Long personId) {
        thePerson = ps.getPersonById(personId);
        return "showCv?faces-redirect=true";
    }
	
	public String getPersonByEmail(String email) {
		thePerson = ps.getPersonByEmail(email);
        return "showPerson?faces-redirect=true";
    }
	
	public List<Person> SearchPerson(){
		if(firstName != "") 
			return ps.findPersonsByFirstName(firstName);
		else if(lastName != "")
			return ps.findPersonsByLastName(lastName);
		else
			return null;
	}
	
	public List<Person> getPersonsByFirstName(String firstName) {
		return ps.findPersonsByFirstName(firstName);
	}
	
	public List<Person> findPersonsByLastName(String lastName) { 
		return ps.findPersonsByLastName(lastName);
	}
	
	public List<Person> findPersonByActivityTitle(String activity) {
		return ps.findPersonByActivityTitle(activity);
	}
	
}
