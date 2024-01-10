package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import br.com.dyad.client.DataClass;
import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.navigation.persistence.HibernateUtil;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton GonÃ§alves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldClassLookup extends Field{
	
	public static String SEARCH_TOKEN = "@#searchToken#@";
	
	public FieldClassLookup( Grid grid ) throws Exception{
		super(grid);
	}
	
	public FieldClassLookup( Grid grid, String name ) throws Exception{
		super(grid, name);
	}
	
	@SendToClient
	private String beanName;
	@SendToClient
	private Long lookupClassId;
	@SendToClient
	private Integer lookupType;
	@SendToClient
	private Boolean isClassLookup;
	@SendToClient
	private String lookupDisplayValue;
	@SendToClient
	private String findExpress = null;
	
	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(300);
		this.setHeight(1);	
		this.setType(FieldTypes.FIELD_TYPE_CLASS_LOOKUP);
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;		
	}
	
	public Long getLookupClassId() {
		return lookupClassId;
	}

	public void setLookupClassId(Long lookupClassId) {
		this.lookupClassId = lookupClassId;
	}

	public Integer getLookupType() {
		return lookupType;
	}

	public void setLookupType(Integer lookupType) {
		this.lookupType = lookupType;
	}
	
	public Boolean getIsClassLookup() {
		return isClassLookup;
	}

	public void setIsClassLookup(Boolean isClassLookup) {
		this.isClassLookup = isClassLookup;
	}

	public String getFindExpress() {
		return findExpress;
	}

	public void setFindExpress(String findExpress) {
		this.findExpress = findExpress;
	}

	@Override
	public void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();
		
		DataClass entity = (DataClass)value;
		Long id = entity != null ? entity.getClassId() : null;
		addPropNameToPropsToSincronize("value", id, id);
		addPropToClient("value", id);
	}

	public Long getValueId(){
		if ( this.value != null ){
			return ((DataClass)this.value).getClassId();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) throws Exception {		
		if ( value == null ){
			super.setValue(value);
			this.setLookupDisplayValue( null );
		} else {	
			if ( value instanceof DataClass ){
				DataClass entity = (DataClass)value;
				super.setValue(entity);
				this.setLookupDisplayValue( entity.getName() );
			} else {
				if ( value instanceof Long ){
					DataClass entityValue = (DataClass)this.getValue();
					if ( entityValue == null || ! entityValue.getClassId().equals(value) ){  
						HibernateUtil util = new HibernateUtil();
						DataClass root = util.getDataClasses(getDatabase(), null);							
						DataClass clazz = util.findClassByIdentifier( util.getClassTree(), (Long)value );
						this.setValue( clazz );									
					}						
				} else {
					throw new Exception( "Value " + value + " is not valid for a lookup field." );
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValueWithoutEditing(Object value) throws Exception {		
		if ( value == null ){
			super.setValueWithoutEditing(value);
			this.setLookupDisplayValue( null );
		} else {	
			if ( value instanceof DataClass ){
				DataClass entity = (DataClass)value;
				super.setValueWithoutEditing(entity);
				this.setLookupDisplayValue( entity.getName() );
			} else {
				if ( value instanceof Long ){
					DataClass entityValue = (DataClass)this.getValue();
					if ( entityValue == null || ! entityValue.getClassId().equals(value) ){  
						HibernateUtil util = new HibernateUtil();
						DataClass root = util.getDataClasses(getDatabase(), null);							
						DataClass clazz = util.findClassByIdentifier( util.getClassTree(), (Long)value );
						this.setValue( clazz );									
					}	
				} else {
					throw new Exception( "Value " + value + " is not valid for a lookup field." );
				}
			}
		}
	}

	
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
		
		fieldInformation.put("Type", "Class Lookup");
		fieldInformation.put("Lookup to", this.getBeanName() + " (" + BaseEntity.getClassIdentifierByClassName( beanName ) + ")" );
		
		return fieldInformation;
	}	
}