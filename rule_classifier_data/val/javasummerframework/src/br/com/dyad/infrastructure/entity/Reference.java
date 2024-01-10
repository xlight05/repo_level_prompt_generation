package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;

@Entity
@Table(name="SYSREFERENCE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999981")
public class Reference extends BaseEntity{
	
	private String code;
	
	private String name;

	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void defineFields() {
		super.defineFields();
	
		this.defineField(
				"code", 
				MetaField.order, 20,
				MetaField.required, true, 
				MetaField.visible, true, 
				MetaField.width, 100,
				MetaField.label, "Code"
		);

		this.defineField(
				"name", 
				MetaField.order, 30,
				MetaField.required, true, 
				MetaField.visible, true, 
				MetaField.width, 200,
				MetaField.label, "Name"
		);

		this.defineField(
				"description", 
				MetaField.order, 40,
				MetaField.required, false, 
				MetaField.visible, false, 
				MetaField.width, 200,
				MetaField.label, "Description"
		);
	}	

	@Override
	public String toString(){
		if ( this.getCode() != null && this.getName() != null ){
			return this.getCode() + " - " + this.getName();
		} else {
			if ( this.getCode() != null ){
				return this.getCode();
			} else {
				if ( this.getName() != null ){
					return this.getName();
				} else {
					return super.toString();
				}
			}
		}
	}
	
	public static String getFindExpress( String searchToken ){
		return " and ( code like '%" + searchToken + "%' or name like '%" + searchToken + "%' )";
	}	
}
