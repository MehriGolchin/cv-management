package main.services;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import main.models.Activity;

@Stateless
public class ActivityService implements IActivityService {

	@PersistenceContext(unitName = "cvmPersistence")
	private EntityManager em;
	
	@PermitAll
	public List<Activity> getActivities() {
		return em.createQuery("Select a From Activity a", Activity.class).getResultList();
	}
	
	@PermitAll
	public List<Activity> getActivitiesByid(Long personId) {
		//return em.createQuery("Select a From Activity a", Activity.class).getResultList();
		TypedQuery<Activity> query = em.createQuery("From Activity where personId=:personId", Activity.class);
		query.setParameter("personId", personId);
		return query.getResultList();

	}

	//@RolesAllowed({"user"})
	public Activity createActivity(Activity activity) {
		if (activity.getActivityId() == null) // create
		{
			em.persist(activity);
		} else {
			activity = em.merge(activity); // update
		}

		return activity;
	}

	@PermitAll
	public Activity getActivityById(Long activityId) {
		return em.find(Activity.class, activityId);
	}

	//@RolesAllowed({"user"})
	public void deleteActivity(Activity activity) {
		activity = em.merge(activity);
		em.remove(activity);
	}

	@PermitAll
	public List<Activity> findActivitiesByTitle(String title) {
		TypedQuery<Activity> query = em.createQuery("From Activity where lower(title) Like :title", Activity.class);
		query.setParameter("title", "%" + title.toLowerCase() + "%");
		return query.setMaxResults(100).getResultList();
	}

}
