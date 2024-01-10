package br.com.dyad.infrastructure.navigation.admin.backup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.infrastructure.audit.AuditTrail;
import br.com.dyad.infrastructure.entity.ProductLicense;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class Create extends Window {	
	private int order = 0;
	
	private ArrayList<FieldBoolean> licenseField = new ArrayList<FieldBoolean>();
	
	public Create(HttpSession httpSession) {
		super(httpSession);
	}
	
	private void getLicenseCheckBox( Grid grid ) throws Exception {
		List list = PersistenceUtil.executeHql(getDatabase(), "from ProductLicense");
		
		for (Object object : list) {
			ProductLicense license = (ProductLicense)object;
			FieldBoolean field = new FieldBoolean( grid );
			field.setName(license.getName());
			field.setLabel(license.getName());
			field.setOrder(order++);
			field.setData("licenseNumber", license.getId());
			licenseField.add(field);
		}
	}

	protected VariableGrid grid = null; 
	
	Action create = new Action(Create.this, "create"){
		
		@Override
		public void onClick() throws Exception {
			if (!validatePassword()){
				Create.this.alert("Password mismatch!");
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
			
			ArrayList<Long> selectedLics = new ArrayList<Long>();
			for (FieldBoolean temp : licenseField) {
				if (temp.getValue() != null && temp.getValue()){					
					selectedLics.add((Long)temp.getData("licenseNumber"));
				}					
			}
			
			jdbcExport.exportFile((String)grid.getFieldByName("Path").getValue(), (String)grid.getFieldByName("Password").getValue(), selectedLics);
			Create.this.alert("Backup completed!");
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
				
				FieldString path = new FieldString( this );
				path.setRequired(true);
				path.setName("Path");
				path.setOrder(order++);
				
				FieldString password = new FieldString( this );
				password.setPassword(true);
				password.setName("Password");
				password.setOrder(order++);
				
				FieldString confirmPassword = new FieldString( this );
				confirmPassword.setPassword(true);
				confirmPassword.setName("ConfirmPassword");
				confirmPassword.setOrder(order++);
				
				FieldString ignoredTables = new FieldString( this );
				ignoredTables.setName("IgnoredTables");
				ignoredTables.setOrder(order++);
				
				FieldBoolean onlyNegativeKeys = new FieldBoolean( this );
				onlyNegativeKeys.setName("BackupNegativeKeysOnly");
				onlyNegativeKeys.setOrder(order++);
				
				FieldBoolean ignoreLog = new FieldBoolean( this );
				ignoreLog.setName("IgnoreLog");
				ignoreLog.setOrder(order++);
							
				getLicenseCheckBox( this );			
			}		
		};		
	}
}
