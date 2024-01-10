package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.StringPredicate;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldString extends Field{
	
	public FieldString(Grid grid, String name) throws Exception {
		super(grid, name);
	}

	public FieldString(Grid grid) throws Exception {
		super(grid);
	}

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setPassword(false);
		this.setWidth(250);
		this.setHeight(1);	
		this.setType(FieldTypes.FIELD_TYPE_STRING);
	}
	
	@SendToClient
	private String caseType;
	@SendToClient
	private Boolean password;
	@SendToClient
	protected Boolean onlyNumbers;
	@SendToClient
	protected Boolean allowDecimals;
	
	public Boolean getAllowDecimals() {
		return allowDecimals;
	}

	public void setAllowDecimals(Boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}

	public Boolean getOnlyNumbers() {
		return onlyNumbers;
	}

	public void setOnlyNumbers(Boolean onlyNumbers) {
		this.onlyNumbers = onlyNumbers;
	}

	public Boolean getPassword() {
		return password;
	}

	public void setPassword(Boolean password) {
		this.password = password;
	}
	
	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getValue(){
		return super.getValue() != null ? super.getValue().toString() : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "String");
		
		return fieldInformation;
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		String token = (String)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new StringPredicate( fieldName, token, new ComparatorFieldGetter(){
				@Override
				public Object getComparateValue(Object entity, String fieldName) {
					BaseEntity base = (BaseEntity)entity;
					return ReflectUtil.getFieldValue(entity, fieldName);
				}				
			} ));
		}
		return this.predicate;
	}
}
