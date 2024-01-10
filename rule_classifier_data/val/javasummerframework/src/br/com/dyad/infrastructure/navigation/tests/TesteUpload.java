package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldUpload;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class TesteUpload extends Window{

	public TesteUpload(HttpSession httpSession) {
		super(httpSession);
	}
		
	Interaction backup = new Interaction(this, "Backup"){
		@Override
		public void defineInteraction() throws Exception {
			add(new GridUpload(this.getWindow(), "GridDeTeste"));
		}
		
	};
	
	@Override
	public void defineWindow() {		
	}

}

class GridUpload extends VariableGrid{

	private FieldUpload uploadField;
			
	public GridUpload(Window window, String name) throws Exception {
		super(window, name);
		uploadField = new FieldUpload(this, "uploadField");
	}
	
	public FieldUpload getUploadField() {
		return uploadField;
	}

	public void setUploadField(FieldUpload uploadField) {
		this.uploadField = uploadField;
	}

	@Override
	public void defineGrid() throws Exception {
	}
	
}
