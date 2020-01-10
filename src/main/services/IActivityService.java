package main.services;

import java.util.List;
import javax.ejb.Local;
import main.models.Activity;

@Local
public interface IActivityService {

	List<Activity> getActivities();
	List<Activity> getActivitiesByid(Long personId);
	Activity createActivity(Activity activity);
	Activity getActivityById(Long activityId);
	void deleteActivity(Activity activity);
	void deleteActivitiesByPersonId(Long personId);
	List<Activity> findActivitiesByTitle(String title);
	
}
