package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class FieldMemo extends Field{

	public FieldMemo(Grid grid) throws Exception{
		super(grid);
	}
	
	public FieldMemo(Grid grid, String name ) throws Exception{
		super(grid, name);
	}
	
	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(250);
		this.setHeight(3);			
		this.setType(FieldTypes.FIELD_TYPE_MEMO);
	}

	public String getValue(){
		return (String)super.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Memo");
		
		return fieldInformation;
	}	

}
