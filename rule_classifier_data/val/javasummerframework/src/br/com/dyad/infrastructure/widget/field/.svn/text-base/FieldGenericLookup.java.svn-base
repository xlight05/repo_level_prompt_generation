package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Danilo Sampaio (danilo@dyad.com.br)
 */
public class FieldGenericLookup extends Field{
	
	public static String SEARCH_TOKEN = "@#searchToken#@";
	
	public FieldGenericLookup( Grid grid ) throws Exception{
		super(grid);
	}
	
	public FieldGenericLookup( Grid grid, String name ) throws Exception{
		super(grid, name);
	}

	private String beanName;
	private Long lookupClassId;
	private Integer lookupType;
	private Boolean isClassLookup;
	private String lookupDisplayValue;		
	private String findExpress = null;
	
	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(300);
		this.setHeight(1);	
		this.setType(FieldTypes.FIELD_TYPE_LOOKUP);
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		addPropNameToPropsToSincronize("beanName", this.beanName, beanName);
		this.beanName = beanName;		
	}
	
	public Long getLookupClassId() {
		return lookupClassId;
	}

	public void setLookupClassId(Long lookupClassId) {
		addPropNameToPropsToSincronize("lookupClassId", this.lookupClassId, lookupClassId);
		this.lookupClassId = lookupClassId;
	}

	public Integer getLookupType() {
		return lookupType;
	}

	public void setLookupType(Integer lookupType) {
		addPropNameToPropsToSincronize("lookupType", this.lookupType, lookupType);
		this.lookupType = lookupType;
	}
	
	public Boolean getIsClassLookup() {
		return isClassLookup;
	}

	public void setIsClassLookup(Boolean isClassLookup) {
		addPropNameToPropsToSincronize("isClassLookup", this.isClassLookup, isClassLookup);
		this.isClassLookup = isClassLookup;
	}

	public String getFindExpress() {
		return findExpress;
	}

	public void setFindExpress(String findExpress) {
		addPropNameToPropsToSincronize("findExpress", this.findExpress, findExpress);
		this.findExpress = findExpress;
	}

	@Override
	public void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();
		addPropToClient("value", this.getValueId() );
		addPropToClient("beanName", this.getBeanName() );
		addPropToClient("lookupClassId", this.getLookupClassId() );
		addPropToClient("lookupType", this.getLookupType() );
		addPropToClient("isClassLookup", this.getIsClassLookup() );
		addPropToClient("lookupDisplayValue", this.getLookupDisplayValue() );
		addPropToClient("findExpress", this.getFindExpress() );
	}

	public Long getValueId(){
		//-- Deve ser implementado nas classes filhas
		return null;
	};
	
	@Override
	public void setValue(Object value) throws Exception{
		//-- Deve ser implementado nas classes filhas
	}; 
	
	@Override
	public void setValueWithoutEditing(Object value) throws Exception{
		//-- Deve ser implementado nas classes filhas
	};

	
	public String getLookupDisplayValue() {
		return lookupDisplayValue;
	}

	public void setLookupDisplayValue(String lookupDisplayValue) {
		addPropNameToPropsToSincronize("lookupDisplayValue", this.lookupDisplayValue, lookupDisplayValue);
		this.lookupDisplayValue = lookupDisplayValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Lookup");
		fieldInformation.put("Lookup to", this.getBeanName() + " (" + BaseEntity.getClassIdentifierByClassName( beanName ) + ")" );
		
		return fieldInformation;
	}	
}