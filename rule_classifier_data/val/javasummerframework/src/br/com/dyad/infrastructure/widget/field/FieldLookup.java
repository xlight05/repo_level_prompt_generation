package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.IntegerPredicate;
import br.com.dyad.infrastructure.widget.predicate.LookupPredicate;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldLookup extends Field{
	
	public static String SEARCH_TOKEN = "@#searchToken#@";
	
	public FieldLookup( Grid grid ) throws Exception{
		super(grid);
	}
	
	public FieldLookup( Grid grid, String name ) throws Exception{
		super(grid, name);
	}

	@SendToClient
	private String beanName;
	@SendToClient
	private Long lookupClassId;
	@SendToClient
	private Integer lookupType;
	@SendToClient
	private String lookupDisplayValue;
	@SendToClient
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
	
	public String getFindExpress() {
		return findExpress;
	}

	public void setFindExpress(String findExpress) {
		this.findExpress = findExpress;
	}

	public Long getValueId(){
		if ( this.value != null ){
			return ((BaseEntity)this.value).getId();
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
			if ( value instanceof BaseEntity ){
				BaseEntity entity = (BaseEntity)value;
				super.setValue(entity);
				this.setLookupDisplayValue( entity.toString() );
			} else {
				if ( value instanceof Long ){
					BaseEntity entityValue = (BaseEntity)this.getValue();
					if ( entityValue == null || ! entityValue.getId().equals(value) ){
						String query = " from " + this.getBeanName() + " where id = " +  value;
						List<BaseEntity> listBaseEntity = PersistenceUtil.executeHql(getDatabase(), query);
						if ( listBaseEntity.size() != 1 ){
							throw new Exception( "Cannot find entity with id " + value + " in " + this.getBeanName() + "." );
						}
						BaseEntity entity = (BaseEntity)listBaseEntity.get(0);
						this.setValue( entity );
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
			if ( value instanceof BaseEntity ){
				BaseEntity entity = (BaseEntity)value;
				super.setValueWithoutEditing(entity);
				this.setLookupDisplayValue( entity.toString() );
			} else {
				if ( value instanceof Long ){
					BaseEntity entityValue = (BaseEntity)this.getValue();
					if ( entityValue == null || ! entityValue.getId().equals(value) ){
						String query = " from " + this.getBeanName() + " where id = " +  value;
						List<BaseEntity> listBaseEntity = PersistenceUtil.executeHql(getDatabase(), query);
						if ( listBaseEntity.size() != 1 ){
							throw new Exception( "Cannot find entity with id " + value + " in " + this.getBeanName() + "." );
						}
						BaseEntity entity = (BaseEntity)listBaseEntity.get(0);
						this.setValueWithoutEditing( entity );
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
	
	@Override
	protected void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();
		
		BaseEntity entity = (BaseEntity)value;
		Long id = entity != null ? entity.getId() : null;
		addPropNameToPropsToSincronize("value", id, id);
		addPropToClient("value", id);
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		String token = (String)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new LookupPredicate( fieldName, token, new ComparatorFieldGetter(){
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
