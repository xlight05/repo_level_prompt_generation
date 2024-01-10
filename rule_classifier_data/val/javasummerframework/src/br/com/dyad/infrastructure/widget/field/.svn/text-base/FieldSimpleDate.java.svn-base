package br.com.dyad.infrastructure.widget.field;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.LookupPredicate;
import br.com.dyad.infrastructure.widget.predicate.SimpleDatePredicate;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldSimpleDate extends Field{
	
	public FieldSimpleDate(Grid grid, String name) throws Exception {
		super(grid, name);
	}

	public FieldSimpleDate(Grid grid) throws Exception {
		super(grid);
	}

	@SendToClient
	private String dateFormat;

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(100);
		this.setDateFormat( "dd/MM/yyyy" );
		this.setType(FieldTypes.FIELD_TYPE_SIMPLEDATE);
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Date getValue(){
		return (Date)super.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Date");
		
		return fieldInformation;
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		String token = (String)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new SimpleDatePredicate( fieldName, token, new ComparatorFieldGetter(){
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
