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
@Table(name="SYSUSERPROFILE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999887")
public class UserFieldValue extends UserProfile{
	private String completeFieldName;
	private String fieldValue;
	private byte[] fieldValues;
	
	/**
	 * LookupField only has fieldIdValue.
	 */
	private Long fieldIdValue;
	
	public Long getFieldIdValue() {
		return fieldIdValue;
	}
	public void setFieldIdValue(Long fieldIdValue) {
		this.fieldIdValue = fieldIdValue;
	}
	public String getCompleteFieldName() {
		return completeFieldName;
	}
	public void setCompleteFieldName(String completeFieldName) {
		this.completeFieldName = completeFieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public byte[] getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(byte[] fieldValues) {
		this.fieldValues = fieldValues;
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
				"completeFieldName", 
				MetaField.order, 20,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, true,
				MetaField.readOnly, true,
				MetaField.label, "Complete Field Name"
			);
		this.defineField(
				"fieldValue", 
				MetaField.order, 20,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, true,
				MetaField.readOnly, true,
				MetaField.label, "Field Value"
			);
		this.defineField(
				"fieldIdValue", 
				MetaField.order, 20,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, true,
				MetaField.readOnly, true,
				MetaField.label, "Id Value"
			);
	}
}
