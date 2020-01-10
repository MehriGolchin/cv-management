package main.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.inject.Named;
import javax.persistence.*;

@Entity
@Named
public class Person implements Serializable {

private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade=CascadeType.DETACH)
    @JoinColumn(name="personId")
    private Set<Activity> activities;

	@Id
	@GeneratedValue
	private Long personId;

	@Column(nullable = false, length = 100)
	private String lastName;

	@Column(nullable = false, length = 100)
	private String firstName;

	@Column(nullable = false, length = 200, unique = true)
	private String email;

	@Column(nullable = true)
	private String webSite;

	@Column(nullable = true)
	private Date birthDate;

	@Column(nullable = false, length = 20)
	private String password;

	public Person() {
	}

	public Person(String firstName, String lastName, String email, String webSite, Date birthDate, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.webSite = webSite;
		this.birthDate = birthDate;
		this.password = password;
	}

	// Accessor Methods
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

}
