package main.controllers;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import main.models.Person;
import main.services.ILoginService;

@ManagedBean(name = "login")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	ILoginService ls;

	private String email;
    private String password;
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
        
	public String loginUser()  throws Exception {
		if (SessionUtils.isAuthenticated()) {
			return logout();
		}
		
		Person thePerson = ls.login(email.toLowerCase(), password);
		if (thePerson != null) {

			//HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", thePerson.getEmail());
			session.setAttribute("personId", thePerson.getPersonId());
			return "persons?faces-redirect=true";
		}
		return "failure";
	}
	
	public String logout() {
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	return "login?faces-redirect=true";
	}
	
}
