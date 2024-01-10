package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldBoolean extends Field{
	
	public FieldBoolean(Grid grid) throws Exception{
		super(grid);
	}

	public FieldBoolean(Grid grid, String name ) throws Exception{
		super(grid, name);
	}

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setType(FieldTypes.FIELD_TYPE_BOOLEAN);
	}
	
	public Boolean getValue(){
		return (Boolean)super.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Boolean");
		
		return fieldInformation;
	}	
}