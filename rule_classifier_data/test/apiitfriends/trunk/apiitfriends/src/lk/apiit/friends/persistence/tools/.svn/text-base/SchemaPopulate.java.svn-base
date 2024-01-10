package lk.apiit.friends.persistence.tools;

import java.io.IOException;

import lk.apiit.friends.model.LoginDetails;
import lk.apiit.friends.model.RoleTypes;
import lk.apiit.friends.model.Staff;
import lk.apiit.friends.model.StaffType;
import lk.apiit.friends.model.Student;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

/**
 * Utility Routine to populate Database with test data.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class SchemaPopulate {
	public static void main(String[] args) throws IOException {
		
		SessionFactory sf = SchemaSupport.buildSessionFactory();
		Session session = sf.openSession();
		
		Student student = null;
		Staff staff = null;
		
		// Create Students
		for (int i=1; i<=20;i++) {
			student = new Student();
			student.setCBNo("CB" + String.format("%06d", i));
			student.setEmail("student" + i + "@apiit.lk");
			student.setFirstName("Firstname");
			student.setLastName("Lastname");
			
			LoginDetails lg = new LoginDetails();
			lg.setUser(student);
			lg.setUsername("student" + i);
			lg.setPasswordHash("123");
			lg.setEnabled(true);
			
			lg.addRole(RoleTypes.USER);
			lg.addRole(RoleTypes.STUDENT);
			
			if (i % 5 ==0) {
				lg.addRole(RoleTypes.TECH_SUPPORT);
			}
			
			student.setLoginDetails(lg);

			session.save(student);
		}
		
		// Create Staff
		for (int i=1; i<=20;i++) {
			staff = new Staff();
			staff.setStaffNo("st" + i);
			staff.setEmail("staff" + i + "@apiit.lk");
			staff.setFirstName("Firstname");
			staff.setLastName("Lastname");
			staff.setType(StaffType.LECTURER);
			
			LoginDetails lg = new LoginDetails();
			lg.setUser(staff);
			lg.setUsername("staff" + i);
			lg.setPasswordHash("123");
			lg.setEnabled(true);
			
			lg.addRole(RoleTypes.USER);
			lg.addRole(RoleTypes.STAFF);
			
			if (i%3==0) {
				staff.setType(StaffType.ADMINISTRATION);
			}
			if (i%7==0) {
				staff.setType(StaffType.MANAGEMENT);
			}
			if (i % 5 ==0) {
				
				if (i%20==0 ) {
					lg.addRole(RoleTypes.ACADEMIC_ADMIN);
				}
				else {
					if (i%10 ==0) {
						lg.addRole(RoleTypes.STAFF_ADMIN);
					}
				}
			}
			
			staff.setLoginDetails(lg);

			session.save(staff);
		}
		
		// Create Admin Account
		staff = new Staff();
		staff.setEmail("admin@apiit.lk");
		staff.setFirstName("AdminFirst");
		staff.setLastName("AdminLast");
		staff.setStaffNo("st" + 99);
		staff.setType(StaffType.TECHNICAL);
		
		LoginDetails lg = new LoginDetails();
		lg.setUser(staff);
		lg.setUsername("admin");
		lg.setPasswordHash("123");
		
		lg.addRole(RoleTypes.USER);
		lg.addRole(RoleTypes.STAFF);
		lg.addRole(RoleTypes.STAFF_ADMIN);
		lg.setEnabled(true);
		
		session.save(lg);
		
		staff.setLoginDetails(lg);
		session.save(staff);
	}
}
