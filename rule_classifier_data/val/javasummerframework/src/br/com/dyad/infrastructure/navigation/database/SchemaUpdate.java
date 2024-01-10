package br.com.dyad.infrastructure.navigation.database;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldMemo;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class SchemaUpdate extends Window {
	
	public String ACTION_GENERATE_SCRIPT = "Generate update script";
	public String EXECUTE_GENERATE_SCRIPT = "Execute script";
	
	public SchemaUpdateBean schemaUpdate;
	public String generatedScript = "";
	
	public SchemaUpdate(HttpSession httpSession) {
		super(httpSession);
		this.schemaUpdate = new SchemaUpdateBean(getDatabase());
	}
		
	public Action generateUpdateScript;
	public Action executeScript;
	
	public FieldMemo memo;

	public VariableGrid vg = null;	
	
	Interaction defaultInteraction = new Interaction(this, "default"){		
		@Override
		public void defineInteraction() throws Exception {
			SchemaUpdate process = (SchemaUpdate) this.getWindow();
			process.enableAndShowActions(ACTION_GENERATE_SCRIPT);
			process.executeScript.show();
			process.executeScript.disable();
			add(vg);
		}
	};
	
	@Override
	public void defineWindow() throws Exception {		
		vg = new VariableGrid(this, "Script"){		
			public void defineGrid() throws Exception {
				this.setTitle("Script");
				
				memo.setLabel("Generated Sql Script");
				memo.setWidth(300);			
				memo.setHeight(200);
				memo.setReadOnly(true);
				this.add(memo);
			}
		};
		
		memo = new FieldMemo(vg);
		
		this.generateUpdateScript = new Action(this, ACTION_GENERATE_SCRIPT ){
			public void onClick() throws Exception{
				generatedScript = schemaUpdate.getDataBaseChanges(SchemaUpdateBean.ACTION_UPDATE);
				if (generatedScript != null && ! generatedScript.equals("") ){
					memo.setValue(generatedScript);
					executeScript.enable();
					this.disable();
				} else {
					SchemaUpdate.this.alert("No changes on database!");
				}
			};
		};

		this.executeScript = new Action(this, EXECUTE_GENERATE_SCRIPT){
			@Override
			public void onClick() throws Exception {
				String script = memo.getValue();
				if ( script != null && ! script.equals("") ){
					schemaUpdate.executeDataBaseChanges( script );
					this.disable();
					SchemaUpdate.this.alert("Database updated with sucess!");
				}	
			}
		};
		
		//add(executeScript);
		//add(defaultInteraction);
	}	
}