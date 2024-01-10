package br.com.dyad.infrastructure.navigation.admin.security;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class Users extends GenericEntityBeanWindow {
	
	//---------------------
	//-- 1st Interaction -  Show Data
	//---------------------

	public Users(HttpSession httpSession) {
		super(httpSession);
	}

	Action changePassword = new Action(this, "Change Password"){
		@Override
		public void onClick() throws Exception {
			Users processUsers = (Users)this.getParent();
			processUsers.setNextInteraction( changePasswordInteraction );
		}
	};
	
	public InteractionShowData showData = new InteractionShowData(this, "showData"){
		@Override
		public void defineInteraction() throws Exception {
			super.defineInteraction();			

			Users processUsers = (Users)this.getWindow();
			processUsers.enableAndShowActions( processUsers.changePassword  );
		}
	};

	//---------------------
	//-- 2nd Interaction -  Change Password
	//---------------------

	
	Action cancelChangingPassword = new Action( Users.this, "Cancel"){
		@Override
		public void onClick() throws Exception {
			Users processUsers = (Users)this.getParent();
			processUsers.setNextInteraction( processUsers.showData );
		}
	};
	
	Action actionChange = new Action( Users.this, "Change"){
		@Override
		public void onClick() throws Exception {
			Users processUsers = (Users)this.getParent();
			
			ChangePasswordVariableGrid passwordGrid = processUsers.passwordGrid;
			if ( passwordGrid.confirmNewPassword.getValue() == null || passwordGrid.newPassword.getValue() == null ){
				processUsers.alert("You must define a new password and confirm it.");
			} else {
				if ( ! passwordGrid.confirmNewPassword.getValue().equals( passwordGrid.newPassword.getValue() ) ){
					processUsers.alert("Password and confirmation do not match.");
				} else {
					processUsers.alert("Password was changed sucessfuly.");
					
					DataGrid dataGrid = processUsers.grid;		
					dataGrid.getFieldByName("password").setValue( passwordGrid.newPassword.getValue() );
					
					dataGrid.dispatchPost();
					
					processUsers.setNextInteraction( processUsers.showData );
				}
			}
		}
	};
	
	public ChangePasswordVariableGrid passwordGrid = null;
	
	public Interaction changePasswordInteraction = new Interaction(this, "changePasswordInteraction"){
		@Override
		public void defineInteraction() throws Exception {
			Users processUsers = (Users)this.getWindow();
			
			processUsers.enableAndShowActions( processUsers.cancelChangingPassword );
			processUsers.enableAndShowActions( processUsers.actionChange );
			
			DataGrid dataGrid = processUsers.grid;		
			ChangePasswordVariableGrid passwordGrid = processUsers.passwordGrid;
						
			this.add( processUsers.passwordGrid );
			
			passwordGrid.clearFields();
			passwordGrid.id.setValue( dataGrid.getFieldByName("id").getValue() );
			passwordGrid.login.setValue( dataGrid.getFieldByName("login").getValue() );
		}
	};
	

	//---------------------
	//-- Window definition
	//---------------------
	
	@Override
	public Interaction getShowData() {		
		return this.showData;
	}	
	
	@Override
	public void defineWindow() throws Exception {
		passwordGrid = new ChangePasswordVariableGrid("passwordGrid");
		
		this.setBeanName( "br.com.dyad.infrastructure.entity.User" );
		
		super.defineWindow();
		
		this.cancelChangingPassword.setValidateLastInteraction(false);
		
		this.setHelp( "This window manipulates all user configuration from users of this application. " + 
				      "Here, you can create, update and delete users. Change passwords and choose different " +
				      "user groups are avaliable."
		);
	}
	
	class ChangePasswordVariableGrid extends VariableGrid{
		public ChangePasswordVariableGrid(String name) throws Exception {
			super(Users.this, name);
			this.setTitle("Change Password");
		}

		public FieldInteger id;
		public FieldString login;
		public FieldString newPassword;
		public FieldString confirmNewPassword;
		
		@Override
		public void defineGrid() throws Exception {
			id = new FieldInteger(this);
			login = new FieldString(this);
			newPassword = new FieldString(this);
			confirmNewPassword = new FieldString(this);
			
			id.setName("id");
			id.setOrder(0);
			id.setLabel("Id");
			id.setRequired(true);
			id.setReadOnly(true);
			id.setWidth(100);			

			login.setName("login");
			login.setOrder(100);
			login.setLabel("Login");
			login.setRequired(true);
			login.setReadOnly(true);
			login.setWidth(100);			

			newPassword.setName("newPassword");
			newPassword.setOrder(200);
			newPassword.setLabel("New Password");
			newPassword.setPassword(true);
			newPassword.setRequired(true);
			newPassword.setWidth(100);			
			
			confirmNewPassword.setName("confirmNewPassword");
			confirmNewPassword.setOrder(300);
			confirmNewPassword.setLabel("Confirmation");
			confirmNewPassword.setPassword(true);
			confirmNewPassword.setRequired(true);
			confirmNewPassword.setWidth(100);
		}
	}	
};