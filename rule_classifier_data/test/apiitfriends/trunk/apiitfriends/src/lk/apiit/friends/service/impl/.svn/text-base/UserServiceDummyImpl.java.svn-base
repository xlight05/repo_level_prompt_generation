package lk.apiit.friends.service.impl;

import java.util.HashMap;
import java.util.Map;

import lk.apiit.friends.model.LoginDetails;
import lk.apiit.friends.model.RoleTypes;
import lk.apiit.friends.model.Staff;
import lk.apiit.friends.model.Student;
import lk.apiit.friends.model.User;
import lk.apiit.friends.service.UserService;
import lk.apiit.friends.service.auth.AuthenticatedUser;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * Dummy Implementation of UserService interface.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-Sep-2008
 * @since 04-Sep-2008
 */
public class UserServiceDummyImpl implements UserService{

	// Dummy
	private Map<String, User> users = new HashMap<String, User>();
	
	{
		Student student = null;
		LoginDetails lg = null;
		Staff staff = null;
		
		for (int i=0;i<10;i++) {
			student = new Student();
			student.setCBNo("CB" + i);
			student.setFirstName("fname" + i);
			
			lg = new LoginDetails();
			lg.setUser(student);
			lg.setUsername("student" + i);
			lg.setPasswordHash(String.valueOf(i));
			lg.setEnabled(true);
			lg.addRole(RoleTypes.USER);
			lg.addRole(RoleTypes.STUDENT);
			student.setLoginDetails(lg);
			
			users.put(lg.getUsername(), student);
		}
		
		for (int i=0;i<10;i++) {
			staff = new Staff();
			staff.setStaffNo("staff" + i);
			staff.setFirstName("fname" + i);
			
			lg = new LoginDetails();
			lg.setUser(staff);
			lg.setUsername("staff" + i);
			lg.setPasswordHash(String.valueOf(i));
			lg.setEnabled(true);
			lg.setLocked(false);
			lg.setExpired(false);
			lg.addRole(RoleTypes.USER);
			lg.addRole(RoleTypes.STAFF);
			staff.setLoginDetails(lg);
			
			users.put(lg.getUsername(), staff);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return new AuthenticatedUser(users.get(username));
	}

	

	@Override
	public <T extends User> T findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public <T extends User> T findUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public <T extends User> T findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void persist(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isCBNoFree(String cbNo) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isStaffNoFree(String staffNo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
