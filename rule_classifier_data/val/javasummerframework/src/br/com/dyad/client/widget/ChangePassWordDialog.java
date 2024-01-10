package br.com.dyad.client.widget;

import br.com.dyad.client.KeyCodes;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

@SuppressWarnings("unchecked")
public class ChangePassWordDialog extends Dialog {

	protected ToolBar buttonBar;
	protected TextField<String> userName;
	protected TextField<String> password;
	protected TextField<String> confirmPassword;
	protected Button cancel;
	protected Button change;
	protected ModelData bean;
	protected ListStore store;
	
	public ChangePassWordDialog( ModelData bean, ListStore store) {
		this.bean = bean;
		this.store = store;
		
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);

		setButtons("");
		setIconStyle("login-user");
		setHeading("Change Password");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(300);
		setResizable(false);

		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				validateFilledFields();	
				if (event.getKeyCode() == 20 /* Caps Lock */){
					Info.display("Atenção", "Caps Lock está ativado!");
				}				
				
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					if (event.getComponent().getId().equals("x-auto-11" /* User name */)) {						
						if (change.isEnabled()) {
							onSubmit();
						} else {
							setFocusWidget(password);
						}
					} else {
						if (change.isEnabled()) {
							onSubmit();
						}
					}
				}
			}
		};
		
		userName = new TextField<String>();
		userName.setMinLength(4);
		userName.setFieldLabel("Username");
		userName.addKeyListener(keyListener);
		userName.setReadOnly(true);
		userName.disable();
		userName.setValue((String)bean.get("login"));
		add(userName);

		password = new TextField<String>();
		//password.setMinLength(4);
		password.setPassword(true);
		password.setFieldLabel("Password");
		password.addKeyListener(keyListener);
		add(password);

		confirmPassword = new TextField<String>();
		//confirmPassword.setMinLength(4);
		confirmPassword.setPassword(true);
		confirmPassword.setFieldLabel("Confirm");
		confirmPassword.addKeyListener(keyListener);
		add(confirmPassword);
		
		setFocusWidget(password);

		buttonBar = new ToolBar();
		setBottomComponent(buttonBar);		
	}

	@Override
	protected void createButtons() {
		cancel = new Button("Cancel");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				ChangePassWordDialog.this.hide();
			}
		});

		change = new Button("Change");
		change.disable();
		change.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		buttonBar.add(cancel);
		buttonBar.add(change);
	}

	protected void onSubmit() {
		validate();
	}

	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	protected void validateFilledFields() {
		boolean informedValue = hasValue(userName) && hasValue(password) && hasValue(confirmPassword) 
		                        && password.getValue().length() > 3 && confirmPassword.getValue().length() > 3;
		change.setEnabled(informedValue);
	}

	protected void validate() {
		if ( hasValue(password) && hasValue(confirmPassword) && hasValue(userName)  ){
			if ( password.getValue().equals( confirmPassword.getValue() )){
				String newPass = password.getValue();
				newPass.trim();				
				this.bean.set("password", newPass);
				this.store.update(bean);
				System.out.println("Salvou!!! Pass: " + newPass);
				Info.display("Change password", "Password was change with sucess!");
				this.hide();
			} else {
				Info.display("Change password", "Password does not match with confirmation!");
			}
		} else {
			Info.display("Change password", "You must define and confirm a new password!");
		}	
	}		
}