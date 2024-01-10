package br.com.dyad.infrastructure.widget.field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.IntegerPredicate;
import br.com.dyad.infrastructure.widget.predicate.NumberPredicate;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldNumber extends FieldAbstractNumber{

	public FieldNumber(Grid grid) throws Exception {
		super(grid);
	}

	public FieldNumber(Grid grid, String name) throws Exception {
		super(grid, name);
	}	
	
	@Override
	protected void initializeField() {
		super.initializeField();
		this.setDecimalPrecision(2);
		this.setWidth(120);
		this.setType(FieldTypes.FIELD_TYPE_NUMBER);
	}
	
	public Object getValue(){
		return this.value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Number");
		
		return fieldInformation;
	}	
	
	public Double getDoubleValue() {
		if (value == null) {
			return null;			
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal)value).doubleValue();			
		} else if (value instanceof BigInteger) {
			return ((BigInteger)value).doubleValue();			
		} else {
			return (Double)value;
		}
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		Double token = (Double)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new NumberPredicate( fieldName, token, new ComparatorFieldGetter(){
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
