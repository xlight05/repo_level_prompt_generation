package lk.apiit.friends.persistence.impl.jpa;

import lk.apiit.friends.model.User;
import lk.apiit.friends.persistence.ObjectNotFoundException;
import lk.apiit.friends.persistence.UserDao;

import org.springframework.dao.DataAccessException;

/**
 * UserDAO Implementation using JPA.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class UserDaoJpaImpl extends AbstractBaseDaoJpa<User> implements UserDao {

	/**
	 * No-args Constructor
	 */
	public UserDaoJpaImpl() {
		super(User.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserByEmail(String email) throws DataAccessException, ObjectNotFoundException {
		
		email = email.toLowerCase();
		
		try {
			return (User) getJpaTemplate().find("from User u where u.email=?1",email).get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new ObjectNotFoundException("User not found for email : " + email);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserByUsername(String username) throws DataAccessException, ObjectNotFoundException  {
		
		username  = username.toLowerCase();
		
		try {
			return (User) getJpaTemplate().find("from User u where u.loginDetails.username=?1",username).get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new ObjectNotFoundException("User not found for username : " + username);
		}
	}

	@Override
	public boolean isCBNoFree(String cbNo) throws DataAccessException {
		cbNo = cbNo.toUpperCase();
		return getJpaTemplate().find("from Student s where s.CBNo=?1",cbNo).size()==0;
	}

	@Override
	public boolean isStaffNoFree(String staffNo) throws DataAccessException {
		return getJpaTemplate().find("from Staff s where s.staffNo=?1",staffNo).size()==0;
	}

}
