package br.com.dyad.infrastructure.navigation.database;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.ActionMenuItem;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class RecordUpdate extends Window {

	Action actionSearch;	
	ActionMenuItem actionMenuItem;		
	
	public RecordUpdate(HttpSession httpSession) {
		super(httpSession);
	}

	public VariableGrid target = null;		

	
	Interaction fillVariables = new Interaction( this, "fillVariables" ){
		@Override
		public void defineInteraction() throws Exception {
			RecordUpdate process = (RecordUpdate) this.getWindow();
			process.enableAndShowActions( "Search;Search5" );				
			add( process.target );
		}
	};
	
	Interaction showResult = new Interaction( this, "showResult" ){
		@Override
		public void defineInteraction() {
			RecordUpdate process = (RecordUpdate) this.getWindow();
		}
	};	
	
	@Override
	public void defineWindow() throws Exception {		
		target = new VariableGrid(this, "Informations to record update"){
			@Override
			public void defineGrid() throws Exception {			
				FieldString bean = new FieldString(this, "Bean");
				bean.setRequired(true);		
				bean.setGroup("Record Information");
				
				FieldString product = new FieldString(this, "Product");
				product.setRequired(true);
				
				FieldString recentUpdates = new FieldString(this,"Recent Updates");
				recentUpdates.setRequired(true);
				
				FieldString dialect = new FieldString(this,"Dialect");
				dialect.setRequired(true);
				
				FieldString driver = new FieldString(this,"Driver Class");
				driver.setRequired(true);
				
				FieldString url = new FieldString(this,"Url");
				url.setRequired(true);

				FieldString userName = new FieldString(this,"User Name");
				userName.setRequired(true);
				userName.setGroup("Database target security");

				FieldString targetPwd = new FieldString(this,"Password");
				targetPwd.setPassword(true);

				add( bean );				   				
				add( product );				
				add( recentUpdates );
				add( dialect );
				add( driver );
				add( url );
				add( userName );
				add( targetPwd );
			}
		};
		actionSearch = new Action( this, "Search"){
			public void onClick() throws Exception{
				RecordUpdate process = (RecordUpdate) this.getParent();
				process.searchRecords();
				process.setNextInteraction("showResult");
			}
		};
		
		actionMenuItem = new ActionMenuItem( this, "Search5"){
			public void onClick() throws Exception{
				RecordUpdate process = (RecordUpdate) this.getParent();
				process.searchRecords();
				process.setNextInteraction("showResult");
			}
		};		
		
		
		add( fillVariables );				
		add( showResult );				
	}

	public void searchRecords() {
		
	}
}
