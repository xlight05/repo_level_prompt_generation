package br.com.dyad.infrastructure.widget.field;

import java.util.ArrayList;

import org.apache.commons.collections.Predicate;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.ComparatorFieldGetter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.predicate.ComboPredicate;

public class FieldCombo extends Field {

	@SuppressWarnings("unchecked")
	@SendToClient
	private ArrayList options; 
	
	public FieldCombo(Grid grid ) throws Exception{
		super(grid);
	}

	public FieldCombo(Grid grid, String name ) throws Exception{
		super(grid, name);
	}

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setType(FieldTypes.FIELD_TYPE_COMBO);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList getOptions() {
		return options;
	}

	@SuppressWarnings("unchecked")
	public void setOptions(ArrayList options) {
		this.options = options;
	}

	public void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();
	}	

	@Override
	public void setValue(Object value) throws Exception {		
		if ( value instanceof DyadBaseModel && value != null ){
			DyadBaseModel model = (DyadBaseModel)value;
			Object objValue = model.get("id");
			super.setValue( objValue );
		} else {
			super.setValue( value );
		}
	}
	
	@Override
	public Predicate getPredicate( String fieldName, Object searchToken) {
		String token = (String)searchToken;
		if ( this.predicate == null ){
			this.setPredicate(new ComboPredicate( fieldName, token, new ComparatorFieldGetter(){
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
