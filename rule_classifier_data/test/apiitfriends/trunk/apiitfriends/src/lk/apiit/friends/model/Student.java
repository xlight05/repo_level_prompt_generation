package lk.apiit.friends.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Student User Model.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-September-2008
 * @since 04-September-2008
 */
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

	private static final long serialVersionUID = 7617450539234293343L;

	@Column(unique=true,nullable=false)
	private String CBNo;
	
	public String getCBNo() {
		return CBNo;
	}

	public void setCBNo(String no) {
		CBNo = no.toUpperCase();
	}
	
}
