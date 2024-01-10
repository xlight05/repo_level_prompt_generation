package lk.apiit.friends.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Staff User Model.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-September-2008
 * @since 04-September-2008
 */
@Entity
@DiscriminatorValue("STAFF")
public class Staff extends User {

	private static final long serialVersionUID = -8045296161596554684L;
	
	@Column(unique=true,nullable=false)
	private String staffNo;
	@Enumerated(EnumType.STRING)
	private StaffType type;

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public StaffType getType() {
		return type;
	}

	public void setType(StaffType type) {
		this.type = type;
	}


}
