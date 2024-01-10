package br.com.dyad.infrastructure.file.upload;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldUpload;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class TesteUpload extends Window {
	
	public TesteUpload(HttpSession httpSession) {
		super(httpSession);
	}

	protected VariableGrid grid = null; 
	
	@Authorization(permissionFieldName="download", permissionLabel="Download de arquivo")
	Action download = new Action(TesteUpload.this,"Download"){
		@Override
		public void onClick() throws Exception {
			//String id = (String)grid.getFieldByName("Path").getValue();
			TesteUpload.this.alert(DownloadFile.getDownloadLink(getDatabase(), 923702L));
		}
		
	};	
	
	@Authorization(permissionFieldName="create", permissionLabel="Criar backup")
	Action create = new Action(TesteUpload.this,"create"){		
		@Override
		public void onClick() throws Exception {
			grid.getFieldByName("BackupNegativeKeysOnly").getValue();
		}
		
	};
		
	Interaction backup = new Interaction(this, "Backup"){
		@Override
		public void defineInteraction() throws Exception {
			add(grid);
			getWindow().enableAndShowActions( create );
			getWindow().enableAndShowActions( download );
		}
		
	};
	
	@Override
	public void defineWindow() throws Exception{				
		grid = new VariableGrid( this, "grid") {

			@Override
			public void defineGrid() throws Exception {
				setTitle("Database Backup");
				
				FieldUpload upload = new FieldUpload(this);
				upload.setRequired(true);
				upload.setName("Path");
				upload.setOrder(1);						
			}
			
		};
	};
}
