package br.com.dyad.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="SYSPERMISSION", 
		uniqueConstraints = {@UniqueConstraint(columnNames={"groupid", "classKey"})})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999844")
public class GroupAuthorization extends BaseEntity {
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="groupid")
	private UserGroup userGroup;
	private Long classKey;
	@Lob	
	private String permissionString;
	
	public GroupAuthorization() {		
	}
		
	public String getPermissionString() {
		return permissionString;
	}

	public void setPermissionString(String permissionString) {
		this.permissionString = permissionString;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}
	
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
	public Long getClassKey() {
		return classKey;
	}
	
	public void setClassKey(Long classKey) {
		this.classKey = classKey;
	}

}
