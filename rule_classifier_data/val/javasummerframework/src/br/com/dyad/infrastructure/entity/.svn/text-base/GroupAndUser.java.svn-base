package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;

@Entity
@Table(name="SYSPERMISSION")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999945")
public class GroupAndUser extends Permission{
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid")
	private User user;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="groupid")
	private UserGroup group;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	@Override
	public void defineFields() {
		super.defineFields();		

		this.defineField(
			"user", 
			MetaField.order, 10,
			MetaField.required, true,
			MetaField.width, 200,
			MetaField.visible, false,
			MetaField.label, "User",
			MetaField.beanName, User.class.getName()
		);

		this.defineField(
			"group",
			MetaField.order, 20,
			MetaField.required, true,
			MetaField.width, 200,
			MetaField.label, "Group",
			MetaField.beanName, UserGroup.class.getName()
		);	
	}	
}
