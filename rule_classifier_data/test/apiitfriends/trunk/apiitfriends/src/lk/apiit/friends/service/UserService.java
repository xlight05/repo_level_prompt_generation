package lk.apiit.friends.service;

import lk.apiit.friends.model.User;
import lk.apiit.friends.persistence.ObjectNotFoundException;

import org.springframework.security.userdetails.UserDetailsService;

/**
 * UserService definition. Provides service layer access to various user related
 * operations.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-Sep-2008
 * @since 04-Sep-2008
 */
public interface UserService extends UserDetailsService {

	/**
	 * Returns the User instance for the given User ID (Database Identifier).
	 * 
	 * @param id
	 *            User ID
	 * @return User instance
	 */
	public <T extends User> T findUserById(Long id);

	/**
	 * Returns the User instance for the given user name.
	 * 
	 * @param username
	 *            user name
	 * @return User instance
	 */
	public <T extends User> T findUserByUsername(String username) throws ObjectNotFoundException;

	/**
	 * Returns the User instance for the given email address
	 * 
	 * @param email
	 *            email address
	 * @return User instance
	 */
	public <T extends User> T findUserByEmail(String email) throws  ObjectNotFoundException;
	
	/**
	 * Returns true if CB No is free.
	 * @return
	 */
	public boolean isCBNoFree(String cbNo);
	
	/**
	 * 
	 * @param staffNo
	 * @return
	 */
	public boolean isStaffNoFree(String staffNo);
	
	/**
	 * Persists given User in the Database.
	 * 
	 * @param user User instance
	 */
	public void persist(User user);
	
	/**
	 * Updates the given User in Database.
	 * 
	 * @param user User instance
	 */
	public void update(User user);
	
	/**
	 * Removes the given User from Database.
	 * @param user User instance
	 */
	public void remove(User user);
	
}
