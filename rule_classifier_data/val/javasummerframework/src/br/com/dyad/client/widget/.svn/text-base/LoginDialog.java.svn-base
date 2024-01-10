package br.com.dyad.client.widget;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.dyad.client.ClientUtil;
import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.KeyCodes;
import br.com.dyad.client.TimerMessageBox;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class LoginDialog extends Dialog {

	protected ToolBar buttonBar;
	protected TextField<String> userName;
	protected TextField<String> password;
	protected Button reset;
	protected Button login;
	protected ComboBox<DyadBaseModel> database;
	protected ListStore<DyadBaseModel> connections;
	protected String databaseConnectionName;

	@SuppressWarnings("unchecked")
	public LoginDialog() {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);

		setButtons("");
		setIconStyle("login-user");
		setHeading("User Account");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(300);
		setResizable(false);

		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				validateFilledFields();	
				int keyCode = event.getKeyCode();
				//if ( ( keyCode >= 65 && keyCode <= 90 && !event.isShiftKey() ) || ( keyCode >= 97 && keyCode <= 122 && event.isShiftKey() ) ){
					//Info.display("Atenção", "Caps Lock está ativado!");
				//}
								
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					if (event.getComponent().getId().equals("x-auto-11" /* User name */)) {						
						if (login.isEnabled()) {
							onSubmit();
						} else {
							setFocusWidget(password);
						}
					} else {
						if (login.isEnabled()) {
							onSubmit();
						}
					}
				}
			}
		};
		
		connections = new ListStore<DyadBaseModel>();    				  				
		database = new ComboBox<DyadBaseModel>();
		database.setFieldLabel("Database");
		database.setEmptyText("Select a database...");  
		database.setDisplayField("connection");  
		database.setWidth(250);  		  
		database.setTypeAhead(true);
		database.setTriggerAction(TriggerAction.ALL);
		database.setStore(connections);
				
		add(database);
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();		
		HashMap params = DyadInfrastructure.getRpcParams();
				
		proxy.getServiceValue("br.com.dyad.infrastructure.service.ConnectionService", params, new DyadAsyncCallback(){
			@Override
			public void onSuccess(Object arg0) {
				HashMap params = (HashMap)arg0;
				ArrayList<DyadBaseModel> models = (ArrayList<DyadBaseModel>)params.get("connections");
				connections.add(models);
				database.setValue(models.get(0));
				if ( databaseConnectionName != null ){
					DyadBaseModel model = connections.findModel("connection", databaseConnectionName );
					database.setValue(model);
				}
			}
		});		
		
		userName = new TextField<String>();
		userName.setMinLength(4);
		userName.setFieldLabel("Username");
		userName.addKeyListener(keyListener);
		add(userName);

		password = new TextField<String>();
		password.setMinLength(4);
		password.setPassword(true);
		password.setFieldLabel("Password");
		password.addKeyListener(keyListener);
		add(password);

		//setFocusWidget(userName);				
		setFocusWidget(database);
	}

	public LoginDialog(String user, String database) {
		this();
		userName.setValue(user);
		userName.setEnabled(false);
		
		this.database.setEnabled(false);		
		this.databaseConnectionName = database;
		
		setFocusWidget(password);
	}

	@Override
	protected void createButtons() {
		buttonBar = new ToolBar();
		setBottomComponent(buttonBar);
		
		reset = new Button("Reset");
		reset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				userName.reset();
				password.reset();
				validateFilledFields();
				userName.focus();
			}

		});

		login = new Button("Login");
		login.disable();
		login.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		buttonBar.add(login);
		buttonBar.add(reset);
	}

	protected void onSubmit() {
		if (database.getValue() == null
				|| database.getValue().get("connection") == null
				|| database.getValue().get("connection").equals("")) {
			
			MessageBox.alert("Database", "Select a database to connect", null);
			return;
		}
		
		userName.disable();
		password.disable();
		database.disable();
		//2.0m2
		//buttonBar.getStatusBar().setStyleAttribute("text", "x-status-text");
		//buttonBar.getStatusBar().showBusy("Please wait...");
		buttonBar.disable();
		validate();
	}

	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	protected void validateFilledFields() {
		boolean informedValue = hasValue(userName) && hasValue(password)
				&& password.getValue().length() > 3;
		login.setEnabled(informedValue);
	}

	@SuppressWarnings("unchecked")
	protected void validate() {				
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("login", userName.getValue());
		params.put("password", ClientUtil.md5(password.getValue()));
		params.put(GenericService.GET_DATABASE_FILE, database.getValue().get("connection"));
		params.put(GenericService.GET_REQUEST, "");		
		DyadInfrastructure.database = database.getValue().get("connection");
		
		DyadInfrastructure.messageWindow = TimerMessageBox.wait("Login", "", "Autenticando usuário...");
		DyadInfrastructure.messageWindow.setModal(true);
		
		proxy.getServiceValue("br.com.dyad.infrastructure.service.LoginService",
				params, new DyadAsyncCallback() {
					@Override
					public void onFailure(Throwable arg0) {
						DyadInfrastructure.messageWindow.close();
						userName.enable();
						password.enable();
						database.enable();
						login.setEnabled(true);
						buttonBar.enable();						
						ExceptionDialog.show(arg0);
						setFocusWidget(userName);						
					}

					public void onSuccess(Object arg0) {
						DyadInfrastructure.messageWindow.close();
						HashMap result = (HashMap)arg0;
						DyadInfrastructure.sessionId = (String)result.get(GenericService.SESSION_ID);
						DyadInfrastructure.userName = userName.getValue();
						DyadInfrastructure.userKey = (Long)result.get(GenericService.USER_KEY);
						DyadInfrastructure.clientMessages = (HashMap<String, String>)result.get("clientMessages");
 						
						LoginDialog.this.hide();
						login.setEnabled(true);						
					}
				});
	}
}