package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.ComboPredicate;
import br.com.dyad.infrastructure.widget.predicate.IntegerPredicate;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldInteger extends FieldAbstractNumber{

	public FieldInteger(Grid grid ) throws Exception{
		super(grid);
	}
	
	public FieldInteger(Grid grid, String name ) throws Exception{
		super(grid, name);
	}
	
	@Override
	public void setValue(Object value) throws Exception {
		super.setValue(value);
	}

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(120);
		this.setType(FieldTypes.FIELD_TYPE_INTEGER);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Integer");
		
		return fieldInformation;
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		Long token = (Long)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new IntegerPredicate( fieldName, token, new ComparatorFieldGetter(){
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
