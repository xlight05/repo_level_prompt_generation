package br.com.dyad.infrastructure.widget.field;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.infrastructure.widget.grid.Grid;

public class FieldHtmlEditor extends Field {

	public FieldHtmlEditor(Grid grid, String name) throws Exception {
		super(grid, name);
		setType(FieldTypes.FIELD_TYPE_HTML_EDITOR);
	}

}
