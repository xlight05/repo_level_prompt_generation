/**
 * 
 */
package lk.apiit.friends.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import lk.apiit.friends.model.LoginDetails;
import lk.apiit.friends.model.RoleTypes;
import lk.apiit.friends.model.Student;
import lk.apiit.friends.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JUnit4 Test Case for UserService Implementation.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class UserServiceImplTest {

	private static Log log = LogFactory.getLog(UserServiceImplTest.class);
	
	private UserService service = null;
	
	/**
	 * Retrieves UserService Bean from Spring Container if not retrieved already.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		if (service==null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("services-context.xml");
			service = (UserService) ctx.getBean("userService");
		}
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername() {
		assertNotNull(service.loadUserByUsername("admin"));
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#findUserByEmail(java.lang.String)}.
	 */
	@Test
	public void testFindUserByEmail() {
		assertNotNull(service.findUserByEmail("admin@apiit.lk"));
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#findUserById(java.lang.Long)}.
	 */
	@Test
	public void testFindUserById() {
		assertNotNull(service.findUserById(1L));
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#findUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testFindUserByUsername() {
		assertNotNull(service.findUserByUsername("admin"));
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#persist(lk.apiit.friends.model.User)}.
	 */
	@Test
	public void testPersist() {
		Student st = new Student();
		st.setFirstName("TestFirst");
		st.setLastName("TestLast");
		st.setCBNo("CB900099");
		st.setEmail("teststudent909@apiit.lk");
		
		LoginDetails lg = new LoginDetails();
		lg.setUser(st);
		lg.setEnabled(true);
		lg.setPasswordHash("123");
		lg.setUsername("teststudent909");
		lg.addRole(RoleTypes.STUDENT);
		
		st.setLoginDetails(lg);
		
		service.persist(st);
	}

	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#update(lk.apiit.friends.model.User)}.
	 */
	@Test
	public void testUpdate() {
		Student st = service.findUserByUsername("teststudent909");
		st.setFirstName("Name2");
		service.update(st);
		
		st = service.findUserByUsername("teststudent909");
		assertEquals("Name2", st.getFirstName());
	}


	/**
	 * Test method for {@link lk.apiit.friends.service.impl.UserServiceImpl#remove(lk.apiit.friends.model.User)}.
	 */
	@Test
	public void testRemove() {
		
		Student st = service.findUserByUsername("teststudent909");
		service.remove(st);
		
		try {
			st = service.findUserByUsername("teststudent909");
			fail("No exception thrown");
		} catch (RuntimeException e) {
			log.debug("Exception cuaght",e);
		}
		
	}

}
