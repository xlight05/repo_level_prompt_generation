package br.com.pedro.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.pedro.dao.UserDao;
import br.com.pedro.domain.User;

/**
 * This is a samplee proof-of-concept servlet accessing EJB Dao classes
 * <p>
 * 
 */
@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {

	private static Log log = LogFactory.getLog(UserServlet.class);

	@EJB
	private UserDao userDao;

	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.debug("retrieving data..");

		// Makes a sample DAO call
		Collection<User> users = new ArrayList<User>();
		users.add(userDao.getUser());
		// Populates the request with the retrieved DAO list, so the JSP can
		// display it
		req.setAttribute("users", users);

		// forward to the view (the JSP)
		getServletConfig().getServletContext()
				.getRequestDispatcher("/users.jsp").forward(req, resp);
	}
}
