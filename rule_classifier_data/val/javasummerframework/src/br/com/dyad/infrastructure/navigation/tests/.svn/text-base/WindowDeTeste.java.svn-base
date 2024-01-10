package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.infrastructure.audit.AuditTrail;
import br.com.dyad.infrastructure.navigation.admin.backup.JdbcExport;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class WindowDeTeste extends Window {

	public WindowDeTeste(HttpSession httpSession) {
		super(httpSession);
	}

	protected VariableGrid grid = null; 
	
	Action create = new Action(WindowDeTeste.this, "create"){
		
		@Override
		public void onClick() throws Exception {
			if (!validatePassword()){
				WindowDeTeste.this.alert("Password mismatch!");
				return;
			}
			
			JdbcExport jdbcExport = new JdbcExport(getDatabase());
			
			if (grid.getFieldByName("IgnoredTables").getValue() != null){				
				String tbl = (String)grid.getFieldByName("IgnoredTables").getValue();
				String[] tables = StringUtils.split(tbl, ',');
				
				for (String temp : tables) {				
					jdbcExport.getIgnoredTables().add(temp);
				}
			}
			
			Boolean ignoreLog = (Boolean)grid.getFieldByName("IgnoreLog").getValue();
			if (ignoreLog != null && ignoreLog){
				jdbcExport.getIgnoredTables().add(PersistenceUtil.getClassTableName(getDatabase(), AuditTrail.class));
			}			
			
			Boolean negativeOnly = (Boolean)grid.getFieldByName("BackupNegativeKeysOnly").getValue();
			jdbcExport.setNegativeKeysOnly(negativeOnly != null ? negativeOnly : false);
			
			//jdbcExport.exportFile((String)grid.getFieldByName("Path").getValue(), (String)grid.getFieldByName("Password").getValue());
			WindowDeTeste.this.alert("Backup completed!");
		}
		
	};
	
	private boolean validatePassword() {
		String password = (String)this.grid.getFieldByName("Password").getValue();
		String confirmPassword = (String)this.grid.getFieldByName("ConfirmPassword").getValue();
		
		if (password != null && !password.equals("")){
			return password.equals(confirmPassword);
		}
		
		return true;
	}
		
	Interaction backup = new Interaction(this, "Backup"){
		@Override
		public void defineInteraction() throws Exception {
			add(grid);
			getWindow().enableAndShowActions( create );
		}
		
	};
	
	@Override
	public void defineWindow() throws Exception {				
		grid = new VariableGrid(this, "grid"){

			@Override
			public void defineGrid() throws Exception {
				setTitle("Database Backup");
				
				FieldString path = new FieldString(this);
				path.setRequired(true);
				path.setName("Path");
				path.setOrder(1);
				
				FieldString password = new FieldString(this);
				password.setPassword(true);
				password.setName("Password");
				password.setOrder(2);
				
				FieldString confirmPassword = new FieldString(this);
				confirmPassword.setPassword(true);
				confirmPassword.setName("ConfirmPassword");
				confirmPassword.setOrder(3);
				
				FieldString ignoredTables = new FieldString(this);
				ignoredTables.setName("IgnoredTables");
				ignoredTables.setOrder(4);
				
				FieldBoolean onlyNegativeKeys = new FieldBoolean(this);
				onlyNegativeKeys.setName("BackupNegativeKeysOnly");
				onlyNegativeKeys.setOrder(5);
				
				FieldBoolean ignoreLog = new FieldBoolean(this);
				ignoreLog.setName("IgnoreLog");
				ignoreLog.setOrder(6);
				
				add(path);
				add(password);
				add(confirmPassword);			
				add(ignoredTables);
				add(onlyNegativeKeys);
				add(ignoreLog);
			}
			
		};
	}	
}
