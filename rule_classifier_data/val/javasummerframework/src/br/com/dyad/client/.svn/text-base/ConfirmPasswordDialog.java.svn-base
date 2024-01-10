package br.com.dyad.client;

import java.util.HashMap;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Label;

public class ConfirmPasswordDialog extends Dialog{
	protected ToolBar buttonBar;
	protected TextField<String> userName;
	protected TextField<String> password;
	protected Button exitButton;
	protected Button confirmButton;
	protected Label messageLabel;
	protected DyadClientListener listener;
	protected String message;	
	
	public static void getUserPassword(String message, DyadClientListener listener){
		ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
		dialog.setListener(listener);
		dialog.setMessage(message);
		dialog.showPasswordWindow();
	}
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void showPasswordWindow() {
		
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
								
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					if (event.getComponent().getId().equals("x-auto-11" /* User name */)) {						
						if (confirmButton.isEnabled()) {
							onSubmit();
						} else {
							setFocusWidget(password);
						}
					} else {
						if (confirmButton.isEnabled()) {
							onSubmit();
						}
					}
				}
			}
		};
		
		userName = new TextField<String>();
		userName.setValue(DyadInfrastructure.userName);
		userName.setFieldLabel("Username");
		userName.addKeyListener(keyListener);
		add(userName);
		userName.setReadOnly(true);
		userName.setEnabled(false);

		password = new TextField<String>();
		//password.setMinLength(4);
		password.setPassword(true);
		password.setFieldLabel("Password");
		password.addKeyListener(keyListener);
		add(password);		
				
		messageLabel = new Label(getMessage());
		add(messageLabel);
		
		setFocusWidget(password);				
		
		this.setModal(true);
		this.show();
	}

	public TextField<String> getPassword() {
		return password;
	}

	public void setPassword(TextField<String> password) {
		this.password = password;
	}

	public DyadClientListener getListener() {
		return listener;
	}

	public void setListener(DyadClientListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected void createButtons() {
		buttonBar = new ToolBar();
		setBottomComponent(buttonBar);
		
		exitButton = new Button("Cancel");
		exitButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				ConfirmPasswordDialog.this.setVisible(false);
			}

		});

		confirmButton = new Button("Confirm");
		confirmButton.disable();
		confirmButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		buttonBar.add(confirmButton);
		buttonBar.add(exitButton);
	}
	
	protected void onSubmit() {		
		userName.disable();
		password.disable();
		buttonBar.disable();
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("password", ClientUtil.md5(password.getValue()));				
		proxy.getServiceValue("br.com.dyad.infrastructure.service.ConfirmPasswordService", params, new DyadAsyncCallback(){
			@Override
			public void onFailure(Throwable arg0) {
				super.onFailure(arg0);
				
				userName.enable();
				password.enable();
				buttonBar.enable();
			}
			
			@Override
			public void onSuccess(Object arg0) {						
				userName.enable();
				password.enable();
				buttonBar.enable();
				
				HashMap result = (HashMap)arg0;
				if (listener != null){							
					listener.handleEvent(result);
				}
				Boolean ok = (Boolean)result.get("result");

				if (ok) {							
					ConfirmPasswordDialog.this.setVisible(false);
				} else {
					MessageBox.alert("Invalid user/password", "Invalid user/password!", null);
				}
			}
			
		});
	}
	
	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	protected void validateFilledFields() {
		boolean informedValue = hasValue(userName) && hasValue(password)
				&& password.getValue().length() > 3;
		confirmButton.setEnabled(informedValue);
	}
}
