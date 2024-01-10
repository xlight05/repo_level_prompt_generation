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
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;

@Entity
@Table(name="SYSPERMISSION")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999944")
public class GroupPermission extends Permission{

	@ManyToOne(fetch=FetchType.LAZY)
	private UserGroup group;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private NavigatorEntity navigatorEntity;	

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}
	
	public NavigatorEntity getNavigatorEntity() {
		return navigatorEntity;
	}

	public void setNavigatorEntity(NavigatorEntity navigatorEntity) {
		this.navigatorEntity = navigatorEntity;
	}

	@Override
	public void defineFields() {
		super.defineFields();		

		this.defineField(
				"group",
				MetaField.order, 10,
				MetaField.width, 300,
				MetaField.label, "Group",
				MetaField.beanName, "br.com.dyad.infrastructure.entity.UserGroup"
		);

		this.defineField(
				"navigatorEntity",
				MetaField.order, 20,
				MetaField.width, 300,
				MetaField.label, "Navigator",
				MetaField.beanName, "br.com.dyad.infrastructure.entity.NavigatorEntity"
		);
	}		
}
