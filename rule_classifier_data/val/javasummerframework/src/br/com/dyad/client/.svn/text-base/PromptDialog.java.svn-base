package br.com.dyad.client;

import java.util.HashMap;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Label;

public class PromptDialog extends Dialog{
	protected ToolBar buttonBar;
	protected TextArea userResponse;
	protected Button exitButton;
	protected Button confirmButton;
	protected Label messageLabel;
	protected DyadClientListener listener;
	protected String message;	
	
	public static void getUserResponse(String message, DyadClientListener listener){
		PromptDialog dialog = new PromptDialog();
		dialog.setListener(listener);
		dialog.setMessage(message);
		dialog.showPromptWindow();
	}
	
	public static void getUserConfirmation(String message, DyadClientListener listener){
		final DyadClientListener dyadListener = listener;
		MessageBox.confirm(DyadInfrastructure.translate("Confirmation"), message, new Listener<MessageBoxEvent>(){

			public void handleEvent(MessageBoxEvent be) {
				if (dyadListener != null){
					HashMap<String, String> params = new HashMap<String, String>(); 
					params.put("response", be.getButtonClicked().getText().toLowerCase());
					dyadListener.handleEvent(params);
				}
			}
			
		});
		
	}
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void showPromptWindow() {
		
		TableLayout layout = new TableLayout();
		setLayout(layout);
		
		setButtons("");
		setIconStyle("prompt-user");
		setHeading(DyadInfrastructure.translate("Prompt"));
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(430);
		setResizable(false);

		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				int keyCode = event.getKeyCode();
								
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					if (event.getComponent().getId().equals("x-auto-11" /* User name */)) {						
						if (confirmButton.isEnabled()) {
							onSubmit();
						} else {
							setFocusWidget(userResponse);
						}
					} else {
						if (confirmButton.isEnabled()) {
							onSubmit();
						}
					}
				}
			}
		};
		
		messageLabel = new Label(getMessage());
		add(messageLabel);
		
		userResponse = new TextArea();
		userResponse.setWidth(400);
		userResponse.setHeight(200);
		add(userResponse);								
		
		setFocusWidget(userResponse);				
		
		this.setModal(true);
		this.show();
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
		
		exitButton = new Button(DyadInfrastructure.translate("Cancel"));
		exitButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				PromptDialog.this.setVisible(false);
			}

		});

		confirmButton = new Button(DyadInfrastructure.translate("Confirm"));
		confirmButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		buttonBar.add(confirmButton);
		buttonBar.add(exitButton);
	}
	
	protected void onSubmit() {		
		buttonBar.disable();
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("response", userResponse.getValue());				
		listener.handleEvent(params);		
		this.setVisible(false);
	}
	
	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}
}