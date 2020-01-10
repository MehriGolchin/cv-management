package main.controllers;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
	
	public static HttpSession getSession() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return 	(HttpSession) context.getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getUserName() {
		HttpSession session = getSession();
		return session.getAttribute("username").toString();
	}
	
	public static boolean isAuthenticated() {
		return (getIdentityName() != null);
	}
	
	public static String getIdentityName() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if (context == null) return "";
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		if (request == null) return "";
		String username = request.getRemoteUser();
		if (username != null) return username;
		HttpSession session = request.getSession();
		return (String) session.getAttribute("username");
	}

	public static Long getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (Long) session.getAttribute("personId");
		else
			return null;
	}

}
