package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;
import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.widget.grid.Grid;

public class FieldUpload extends Field {
	@SendToClient
	private Boolean pictureField = false;
	
	public FieldUpload(Grid grid, String name) throws Exception {
		super(grid, name);
	}

	public FieldUpload(Grid grid) throws Exception {
		super(grid);
	}
	
	public Boolean getPictureField() {
		return pictureField;
	}

	public void setPictureField(Boolean pictureField) {
		this.pictureField = pictureField;		
	}

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setWidth(300);
		this.setHeight(1);	
		this.setType(FieldTypes.FIELD_TYPE_UPLOAD);
	}
	
	@Override
	public Long getValue() {
		return (Long)super.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = super.getFieldInformation();
		
		fieldInformation.put("Type", "Integer");
		
		return fieldInformation;
	}
	
	@Override
	public void setValue(Object value) throws Exception {
		Long longValue = value == null ? null : Long.parseLong((String)value); 
		super.setValue(longValue);
	}

}
