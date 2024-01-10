package lk.apiit.friends.model;


/**
 * User Authentication Roles.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-September-2008
 * @since 04-September-2008
 */
public enum RoleTypes {
	
	/**
	 * Normal authenticated user.
	 */
	USER,

	/**
	 * Student User
	 */
	STUDENT,

	/**
	 * Staff User
	 */
	STAFF,

	
	/**
	 * Allowed to create, delete, enable, disable, expire 
	 * staff accounts.
	 */
	STAFF_ADMIN,
	
	/**
	 * Academic Administrator Role. Allowed to create, delete,
	 * enable disable student accounts, expiration of student accounts etc.
	 */
	ACADEMIC_ADMIN,
	
	/**
	 * Technical Support Role (Lab Assistants). Allowed to
	 * lock / unlock student accounts.
	 */
	TECH_SUPPORT,
	
	/**
	 * System Administrator Role.
	 * Allowed to lock, unlock any account.
	 */
	SYS_ADMIN

}
