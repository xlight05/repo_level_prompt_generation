package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;

import br.com.dyad.infrastructure.annotations.MetaField;

@MappedSuperclass
@DiscriminatorValue(value="-89999999999840")
public class Auth extends BaseEntity {	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField(
			"id", 
			MetaField.required, true, 
			MetaField.label, "Id",
			MetaField.visible, true,
			MetaField.readOnly, true
		);
	}		
}
