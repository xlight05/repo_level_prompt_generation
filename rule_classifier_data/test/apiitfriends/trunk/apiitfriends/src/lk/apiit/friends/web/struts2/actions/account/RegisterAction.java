package lk.apiit.friends.web.struts2.actions.account;

import static lk.apiit.friends.service.validations.APIITValidator.isValidCBNo;
import static lk.apiit.friends.service.validations.APIITValidator.isValidStaffNo;
import lk.apiit.friends.model.LoginDetails;
import lk.apiit.friends.model.RoleTypes;
import lk.apiit.friends.model.Staff;
import lk.apiit.friends.model.StaffType;
import lk.apiit.friends.model.Student;
import lk.apiit.friends.persistence.ObjectNotFoundException;
import lk.apiit.friends.service.UserService;
import lk.apiit.friends.web.struts2.actions.AbstractBaseAction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Required;

import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * User Registration Struts2 Action.
 * 
 * @author Yohan Liyanage
 * 
 * @version 16-Sep-2008
 * @since 16-Sep-2008
 */
@Validation
public class RegisterAction extends AbstractBaseAction {

	private static final long serialVersionUID = -907250286521113366L;
	private static Log log = LogFactory.getLog(RegisterAction.class);
	
	private static final String[] types = { "Student", "Staff" };
	private String type;	// Account Types : Student / Staff

	// Staff Type Names
	private static final String[] staffTypes;
	
	/**
	 * Sets staffTypes property to an array of Strings which represents
	 * types of Staff (as per StaffType) in capitalized form.
	 */
	static {
		
		// Get All Staff Types (Uppercase)
		String[] types = new String[StaffType.values().length];
		
		int i = 0;
		
		// Convert to capitalized form (Abc form)
		for (StaffType type : StaffType.values()) {
			types[i++] = StringUtils.capitalize(type.toString().toLowerCase());
		}
		
		staffTypes = types;
	}
	
	/* General Information */
	private String username;
	private String password;
	private String confirm;
	private String email;
	private String firstName;
	private String lastName;
	private String securityQuestion;
	private String securityAnswer;

	/* Student */
	private String CBNo;

	/* Staff */
	private String staffNo;
	private String staffType;
	
	
	/* Dependent Services */
	private UserService userService;
	
	/**
	 * Initial Form Display Action. No validation should occur.
	 * @return SUCCESS always
	 */
	@SkipValidation
	@Override
	public String input() throws Exception {
		return SUCCESS;
	}

	/**
	 * Account Type Selection Action
	 * @return next action to invoke
	 */
	@SkipValidation
	public String selectType() throws Exception {
		return type.toLowerCase();
	}

	/**
	 * Student Account Registration Action
	 * @return
	 * @throws Exception
	 */
	public String registerStudent() throws Exception {
		
		log.debug("Register Student Action Started");
		
		// Validate
		doValidateStudent();
		if (hasFieldErrors()) return INPUT;

		log.debug("Creating Student");
		
		// TODO Refactor : Move this code to service layer?
		Student student = new Student();
		student.setCBNo(CBNo);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		
		student.setLoginDetails(new LoginDetails());
		student.getLoginDetails().setUser(student);
		student.getLoginDetails().setUsername(username);
		student.getLoginDetails().setPasswordHash(password);
		student.getLoginDetails().setSecurityAnswer(securityAnswer);
		student.getLoginDetails().setSecurityQuestion(securityQuestion);
		
		student.getLoginDetails().addRole(RoleTypes.USER);
		student.getLoginDetails().addRole(RoleTypes.STUDENT);
		
		// TODO Make this false to enable email confirmation
		student.getLoginDetails().setEnabled(true);
		
		userService.persist(student);
		return SUCCESS;
	}
	
	/**
	 * Staff Account Registration Action
	 * @return 
	 * @throws Exception
	 */
	public String registerStaff() throws Exception {
		
		log.debug("Register Staff Action Started");
		
		// Validate
		doValidateStaff();
		if (hasFieldErrors()) return INPUT;
		
		log.debug("Creating Staff");

		// TODO Refactor : Move this code to service layer?
		Staff staff = new Staff();
		staff.setStaffNo(staffNo);
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setEmail(email);
		
		staff.setLoginDetails(new LoginDetails());
		staff.getLoginDetails().setUser(staff);
		staff.getLoginDetails().setUsername(username);
		staff.getLoginDetails().setPasswordHash(password);
		staff.getLoginDetails().setSecurityAnswer(securityAnswer);
		staff.getLoginDetails().setSecurityQuestion(securityQuestion);
		
		staff.getLoginDetails().addRole(RoleTypes.USER);
		staff.getLoginDetails().addRole(RoleTypes.STAFF);
		
		// TODO Make this false to enable email confirmation
		staff.getLoginDetails().setEnabled(true);
		
		userService.persist(staff);
		
		return SUCCESS;
	}
	

	/**
	 * Common Validation Logic.
	 */
	private void doValidateCommon() {

		if (!password.equals(confirm)) {
			addFieldError("confirm", "Passwords do not match");
		}

		// Check if username already registered
		try {
			userService.findUserByUsername(username);
			
			// If no ObjectNotFoundException, user exists
			addFieldError("username", "Username is not available");
			
		} catch (ObjectNotFoundException e) {
			// If we get the exception, it means username is available
		}
		
		// Check if email already registered
		try {
			userService.findUserByEmail(email);
			
			// If no ObjectNotFoundException, user exists
			addFieldError("email", "Email address is assigned to another account");
			
		} catch (ObjectNotFoundException e) {
			// If we get the exception, it means username is available
		}
		
	}

	/**
	 * Student Validation Logic.
	 */
	private void doValidateStudent() {

		log.debug("Validating Student Details");
		
		doValidateCommon();
		
		if (! isValidCBNo(CBNo)) {
			// CB No validate format
			addFieldError("CBNo", "CB Number is invalid");
		}
		else {
			// Its in valid format, check if available
			if (!userService.isCBNoFree(CBNo)) {
				addFieldError("CBNo", "Another account has been created for this CB Number");
			}
			// TODO What about CB Number Active Range?
		}
	}
	
	/**
	 * Staff Validation Logic.
	 */
	private void doValidateStaff() {
		
		log.debug("Validating Staff Details");
		
		doValidateCommon();
		
		if (! isValidStaffNo(staffNo)) {
			// StaffNo validate format
			addFieldError("staffNo", "Staff Number is invalid");
		}
		else {
			// Its in valid format, check if available
			if (!userService.isStaffNoFree(staffNo)) {
				addFieldError("staffNo", "Another account has been created for this Staff Number");
			}
			// TODO What about Staff Number Active Range?
		}
	}
	
	public String[] getTypes() {
		return types;
	}


	public String[] getStaffTypes() {
		return staffTypes;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getCBNo() {
		return CBNo;
	}

	public void setCBNo(String no) {
		CBNo = no;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	/**
	 * Sets the UserService Implementation.
	 * This is to be injected by Spring.
	 * 
	 * @param userService UserService Impl
	 */
	@Required
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
}
