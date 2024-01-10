package edu.spbsu.eshop.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.exceptions.UserNotFoundException;

public class AuthBean {
    private String login;
    private String pass;
    private SessionBean sessionBean;
    private boolean remember = false;

    public boolean isRemember() {
	return remember;
    }

    public void setRemember(boolean remember) {
	this.remember = remember;
    }

    public SessionBean getSessionBean() {
	return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
	this.sessionBean = sessionBean;
    }

    public String getLogin() {

	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

    private boolean internalAuth() throws UserNotFoundException {
	if (login == null || pass == null)
	    return false;
	Customer c = Storage.getCustomer(login);
	if (c != null && c.getPassword().equals(pass)) {
	    sessionBean.setUser(c);
	    sessionBean.setAuthorised(true);
	    return true;
	}
	return false;
    }

    public String auth() {
	try {
	    if (internalAuth()) {
		saveCookie(remember);
		return "success";
	    } else {
		ErrorBean.setErrorMessage("Wrong password!");
	    }

	} catch (UserNotFoundException e) {
	    ErrorBean.setErrorMessage("Incorrect login or password!");
	}
	return "fail";
    }

    private void saveCookie(boolean remember) {
	FacesContext facesContext = FacesContext.getCurrentInstance();

	Cookie remCookie = new Cookie("remember", Boolean.toString(remember));
	if (remember) {
	    Cookie login = new Cookie("login", this.login);
	    Cookie passwd = new Cookie("passwd", this.pass);
	    login.setMaxAge(3600);
	    passwd.setMaxAge(3600);
	    ((HttpServletResponse) facesContext.getExternalContext()
		    .getResponse()).addCookie(login);
	    ((HttpServletResponse) facesContext.getExternalContext()
		    .getResponse()).addCookie(passwd);

	}
	remCookie.setMaxAge(3600);
	((HttpServletResponse) facesContext.getExternalContext().getResponse())
		.addCookie(remCookie);

    }

    public boolean authoriseByCookie() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	Cookie cookie[] = ((HttpServletRequest) facesContext
		.getExternalContext().getRequest()).getCookies();
	remember = false;
	if (cookie != null && cookie.length > 0) {
	    for (int i = 0; i < cookie.length; i++) {
		String cookieName = cookie[i].getName();
		if (cookieName.equals("login")) {
		    login = cookie[i].getValue();
		} else if (cookieName.equals("passwd")) {
		    pass = cookie[i].getValue();
		} else if (cookieName.equals("remember")) {
		    try {
			remember = Boolean.parseBoolean(cookie[i].getValue());
		    } catch (Throwable e) {
			// TODO corrupted cookie
			return false;
		    }
		}
	    }
	    if (remember) {
		try {
		    return internalAuth();
		} catch (UserNotFoundException e) {
		    // TODO corrupted cookie
		    return false;
		}
	    }
	}
	return false;
    }

    public void logout() {
	sessionBean.setUser(createUnauthorizedUser());
	sessionBean.setAuthorised(false);
	saveCookie(false);
    }

    public static Customer createUnauthorizedUser() {
	Customer user = new Customer();
	Group group = Storage.getGroupForUnauthorizes();
	user.setGroup(group);
	return user;
    }

}
