/**
 * 
 */
package lk.apiit.friends.persistence;

import org.springframework.dao.DataAccessException;

import lk.apiit.friends.model.User;

/**
 * User Data Access Object, which allows to manage User entities.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * Returns the user for the given user name.
	 * 
	 * @param username user name
	 * @return User
	 */
	public User findUserByUsername(String username) throws DataAccessException ;
	
	/**
	 * Returns the user for the given email address
	 * @param email email address
	 * @return User
	 */
	public User findUserByEmail(String email) throws DataAccessException ;
	
	/**
	 * Returns true if the given CB Number is available for new account.
	 * @param cbNo
	 * @return true if CB No is available for new account
	 * @throws DataAccessException
	 */
	public boolean isCBNoFree(String cbNo) throws DataAccessException;
	
	/**
	 * Returns true if the given staff number is available for new account.
	 * @param staffNo
	 * @return true if Staff No is available for new account
	 * @throws DataAccessException
	 */
	public boolean isStaffNoFree(String staffNo) throws DataAccessException;
}
