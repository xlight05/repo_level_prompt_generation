package br.com.dyad.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;


@Entity
@Table(name="SYSUSER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999949")
public class UserGroup extends Auth {	
	public UserGroup(){}

	protected Boolean admin;
		
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	@Column(name="NAME", length=100 )
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;	
	}
	
	
	@Override
	public void defineFields() {
		super.defineFields();		
		this.defineField(
			"name", 
			MetaField.order, 10,
			MetaField.required, true,
			MetaField.label, "Name");
	}	

	public static String getFindExpress( String searchToken ){
		return " and ( name like '%" + searchToken + "%' )";
	}

	public String toString(){
		return this.getName();
	}

}
