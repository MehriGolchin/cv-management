package main.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistent Fields
	@Id
	@GeneratedValue
	private Long activityId;
	
	@Column(nullable = false)
	private Long personId;
	
	@Column(nullable = false)
	private String year;
	
	//@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private String nature;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = true, length = 5000)
	private String description;
	
	@Column(nullable = true, length = 200)
	private String webSite;
	
//	@ManyToOne
//    private Person person;

	// Constructors
	public Activity() {
		
	}
	
	public Activity(Long personId, String year, String nature, String title, String description, String webSite) {
		this.personId = personId;
		this.year = year;
		this.nature = nature;
		this.title = title;
		this.description = description;
		this.webSite = webSite;
	}

	// Accessor Methods
	public Long getActivityId() {
		return this.activityId;
	}
	
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	public Long getPersonId() {
		return this.personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
}

