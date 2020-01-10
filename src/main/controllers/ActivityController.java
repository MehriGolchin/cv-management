package main.controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import main.models.Activity;
import main.services.IActivityService;

@ManagedBean(name = "activity")
@SessionScoped
public class ActivityController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	IActivityService as;
	
	Activity theActivity = new Activity();
	
	@PostConstruct
    public void init() {
		System.out.println("Create " + this);
        if (as.getActivities().size() == 0) {
 			
			Activity a1 = new Activity();
			a1.setPersonId((long) 1);
			a1.setDescription("fullstack");
			a1.setNature("expérience_professionnelle");
			a1.setTitle("Web developer");
			a1.setWebSite("www.site1.com");
			a1.setYear("2019");

			Activity a2 = new Activity();
			a2.setPersonId((long) 1);
			a2.setDescription("Data science");
			a2.setNature("formation");
			a2.setTitle("BI analyst");
			a2.setWebSite("www.site2.com");
			a2.setYear("2019");

			Activity a3 = new Activity();
			a3.setPersonId((long) 2);
			a3.setDescription("smart devices");
			a3.setNature("projets");
			a3.setTitle("application developer");
			a3.setWebSite("www.site3.com");
			a3.setYear("2019");

			// act
			as.createActivity(a1);
			as.createActivity(a2);
			as.createActivity(a3);
        }
	}
	
	private Long activityId;
	private Long personId;
	private String year;
	private String nature;
	private String title;
	private String description;
	private String webSite;
	
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
	
	public String save() {
		Long personId = SessionUtils.getUserId();
		Activity newActivity = null;

		newActivity = new Activity(personId, year, nature, title, description, webSite);
		
        as.createActivity(newActivity);
        return "showCv?faces-redirect=true";
    }
	
	public String update() {
		as.createActivity(theActivity);
        return "showCv?faces-redirect=true";
	}
	
	public Activity getTheActivity() {
		return theActivity;
	}
	
	public String newActivity() {
		theActivity = new Activity();
        return "editActivity?faces-redirect=true";
    }
	
	public List<Activity> getActivities() {
		return as.getActivities();
	}
	
	public List<Activity> getActivitiesByid(Long personId) {
		return as.getActivitiesByid(personId);
	}
	
	public String show(Long activityId) {
		theActivity = as.getActivityById(activityId);
        return "showActivity?faces-redirect=true";
    }
	
	public String edit(Long activityId) {
		theActivity = as.getActivityById(activityId);
        return "editActivity?faces-redirect=true";
    } 
	
	public List<Activity> getActivitiesByTitle(String title) {
		return as.findActivitiesByTitle(title);
	}

	public String delete(Long activityId) {
		theActivity = as.getActivityById(activityId);
		as.deleteActivity(theActivity);
        return "showCv?faces-redirect=true";
	}
	
}
