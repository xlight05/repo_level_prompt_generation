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

@SuppressWarnings("unused")
@Entity
@Table(name="SYSUSERPROFILE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999925")
public class UserShortcut extends UserProfile {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="navigatorentity")
	private NavigatorEntity navigatorEntity;
	
	public NavigatorEntity getNavigatorEntity() {
		return navigatorEntity;
	}

	public void setNavigatorEntity(NavigatorEntity navigatorEntity) {
		this.navigatorEntity = navigatorEntity;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField("id", 
				MetaField.visible, false);

		this.defineField(
				"user", 
				MetaField.order, 10,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, false,
				MetaField.label, "User",
				MetaField.beanName, "br.com.dyad.infrastructure.entity.User"
			);
		this.defineField(
				"navigatorEntity", 
				MetaField.order, 10,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, true,
				MetaField.tableViewVisible, true,
				MetaField.readOnly, true,
				MetaField.label, "Window",
				MetaField.beanName, "br.com.dyad.infrastructure.entity.NavigatorEntity"
			);
	}
}
