package br.com.dyad.client;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.dyad.client.widget.ExceptionDialog;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Restore implements EntryPoint{
	
	TextField<String> backupFile;  	
	TextField<String> filePassword;
	ComboBox<DyadBaseModel> database;
	ListStore<DyadBaseModel> connections;

	@Override
	public void onModuleLoad() {
		showPasswordWindow();
	}
	
	private void showRestoreWindow() {
		final Window w = new Window();  
		w.setPlain(true);  
		w.setSize(500, 150);  
		w.setHeading("Database Restore");  
		w.setLayout(new FitLayout());  
		
		FormPanel panel = new FormPanel();  
		panel.setBorders(false);  
		panel.setBodyBorder(false);  
		panel.setLabelWidth(80);  
		panel.setPadding(5);  
		panel.setHeaderVisible(false);  
		
		connections = new ListStore<DyadBaseModel>();    				  				
		database = new ComboBox<DyadBaseModel>();
		database.setFieldLabel("Database");
		database.setEmptyText("Select a database to restore...");  
		database.setDisplayField("connection");
		database.setStore(connections);
		panel.add(database, new FormData("100%"));
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();		
		HashMap params = DyadInfrastructure.getRpcParams();
		
		proxy.getServiceValue("br.com.dyad.infrastructure.service.ConnectionService", params, new DyadAsyncCallback(){
			@Override
			public void onSuccess(Object arg0) {
				HashMap params = (HashMap)arg0;
				ArrayList<DyadBaseModel> models = (ArrayList<DyadBaseModel>)params.get("connections");
				connections.add(models);
				database.setValue(models.get(0));
			}
			
		});	
		
		backupFile = new TextField<String>();  
		backupFile.setFieldLabel("Backup file");  
		panel.add(backupFile, new FormData("100%"));
		
		filePassword = new TextField<String>();  
		filePassword.setFieldLabel("FilePassword");
		filePassword.setPassword(true);
		panel.add(filePassword, new FormData("100%"));
		
		Button btRestore = new Button("Restore");
		btRestore.addSelectionListener(new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				confirmRestore();
			}
		});
		w.addButton(btRestore);  
		w.add(panel);
		
		w.show();
	}
	
	private void showPasswordWindow() {
		final Window w = new Window();  
		w.setPlain(true);  
		w.setSize(300, 140);  
		w.setHeading("Database Restore");  
		w.setLayout(new FitLayout());  
		
		FormPanel panel = new FormPanel();  
		panel.setBorders(false);  
		panel.setBodyBorder(false);  
		panel.setLabelWidth(80);  
		panel.setPadding(5);  
		panel.setHeaderVisible(false);  
		
		final TextField<String> field = new TextField<String>();  
		field.setFieldLabel("User");  
		panel.add(field, new FormData("100%"));
		
		final TextField<String> password = new TextField<String>();  
		password.setFieldLabel("Password");
		password.setPassword(true);
		panel.add(password, new FormData("100%"));
		
		Button btLogin = new Button("Login");
		btLogin.addSelectionListener(new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				RestoreServiceAsync proxy = getRestoreProxy();
				proxy.login(ClientUtil.md5(field.getValue()), ClientUtil.md5(password.getValue()), new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						ExceptionDialog.show(caught);
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result){
							w.hide();
							showRestoreWindow();
						} else {
							MessageBox.alert("Login failed", "Invalid user/password", null);
						}
					}
					
				});
			}
		});
		w.addButton(btLogin);  
		w.add(panel);  
		
		w.show();
	}
	
	private void confirmRestore() {
		
		Listener<MessageBoxEvent> confirmListener = new Listener<MessageBoxEvent>(){

			public void handleEvent(MessageBoxEvent be) {
				Button btn = be.getButtonClicked();
				if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("database", (String)database.getValue().get("connection"));
					params.put("backupFile", backupFile.getValue());
					params.put("filePassword", filePassword.getValue());
					executeRestore(params);
				}
			}
			
		};
		
		MessageBox.confirm("Database Restore", "Confirm database restore?", confirmListener);
	}
	
	private void executeRestore(HashMap<String, String> params) {
		DyadInfrastructure.messageWindow = TimerMessageBox.wait("Restore", "Restoring database",
				"restoring...");
		DyadInfrastructure.messageWindow.setModal(true);
		getRestoreProxy().executeRestore(params, new AsyncCallback<Object>(){

			@Override
			public void onFailure(Throwable caught) {
				DyadInfrastructure.messageWindow.close();
				MessageBox.alert("ERROR - Restore failed", caught.getMessage(), null);				
			}

			@Override
			public void onSuccess(Object result) {
				DyadInfrastructure.messageWindow.close();
				MessageBox.alert("Restore completed", "Restore completed", null);				
			}
			
		});
	}
	
	public static RestoreServiceAsync getRestoreProxy(){
		RestoreServiceAsync serviceProxy = (RestoreServiceAsync) GWT.create(RestoreService.class);
		ServiceDefTarget target = (ServiceDefTarget) serviceProxy;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "restoreservice");

		return serviceProxy;
	}

}
