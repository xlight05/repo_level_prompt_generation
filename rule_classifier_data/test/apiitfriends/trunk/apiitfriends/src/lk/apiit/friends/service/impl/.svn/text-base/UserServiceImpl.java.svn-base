package lk.apiit.friends.service.impl;

import lk.apiit.friends.model.User;
import lk.apiit.friends.persistence.UserDao;
import lk.apiit.friends.service.UserService;
import lk.apiit.friends.service.auth.AuthenticatedUser;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserService interface.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	/**
	 * User Data Access Object reference
	 * 
	 * @param userDao UserDao implementation
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// Create an AuthenticatedUser instance of the given user name
		// and return for Spring Security usage
		return new AuthenticatedUser(findUserByUsername(username));
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true)
	@Override
	@SuppressWarnings("unchecked")
	public <T extends User> T findUserByEmail(String email) {
		return (T) userDao.findUserByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true)
	@Override
	@SuppressWarnings("unchecked")
	public <T extends User> T findUserById(Long id) {
		return (T) userDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true)
	@Override
	@SuppressWarnings("unchecked")
	public <T extends User> T findUserByUsername(String username) {
		return (T) userDao.findUserByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void persist(User user) {
		userDao.persist(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void remove(User user) {
		userDao.remove(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public boolean isCBNoFree(String cbNo) {
		return userDao.isCBNoFree(cbNo);
	}

	@Override
	public boolean isStaffNoFree(String staffNo) {
		return userDao.isStaffNoFree(staffNo);
	}

	
}
