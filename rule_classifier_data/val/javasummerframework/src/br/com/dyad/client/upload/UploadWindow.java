package br.com.dyad.client.upload;


import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.TimerMessageBox;
import br.com.dyad.client.gxt.field.customized.UploadFieldCustomized;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;

public class UploadWindow extends Window {
	private UploadFieldCustomized uploadField;
	
	public UploadWindow(UploadFieldCustomized uploadField) {
		
		this.setResizable(false);
		this.setScrollMode(Scroll.NONE);
		this.setHeading("File upload");		
		this.setWidth(500);
		this.setModal(true);
		this.setHeight(180);
		this.uploadField = uploadField;
		this.setLayout(new FormLayout());			
		this.addComponents();
		this.setShadow(false);
		this.setShim(true);
	}
	
	public UploadFieldCustomized getUploadField() {
		return uploadField;
	}

	public void setUploadField(UploadFieldCustomized uploadField) {
		this.uploadField = uploadField;
	}	
	
	private void addComponents() {
		
		final FormPanel panel = new FormPanel();
		panel.setTitle("File Upload");
		panel.setFrame(true);
		panel.setAction("/uploadHandler");
		panel.setEncoding(Encoding.MULTIPART);
		panel.setMethod(Method.POST);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setLayout(new FormLayout());
		panel.setHeight(180);
		panel.setWidth(500);
		
		panel.addListener(Events.Submit, new Listener<FormEvent>(){

			@Override
			public void handleEvent(FormEvent be) {
				Integer number = null;
				try {
					number = Integer.parseInt(be.getResultHtml());
				} catch (Exception e) {
					//Error
				}
				
				if ( number != null ){					
					UploadWindow.this.uploadField.setValue(be.getResultHtml());				
					UploadWindow.this.setVisible(false);
				} else {
					MessageBox.alert("Upload", be.getResultHtml(), null);
				}
				
				DyadInfrastructure.messageWindow.close();
			}
			
		});

		FileUploadField file = new FileUploadField();
		file.setAllowBlank(false);
		file.setFieldLabel("File");		
		file.setName("file");
		file.setAllowBlank(true);
		FormData formData = new FormData();
		formData.setWidth(350);
		panel.add(file, formData);
		
		Button btn = new Button("Upload");
		btn.setHeight(25);
		btn.setWidth(70);
		btn.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				panel.submit();
				DyadInfrastructure.messageWindow = TimerMessageBox.wait("Upload", "Uploading file", "uploading...");
				DyadInfrastructure.messageWindow.setModal(true);
			}
		});
		
		Button btnCancel = new Button("Cancel");
		btnCancel.setHeight(25);
		btnCancel.setWidth(70);
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UploadWindow.this.setVisible(false);				
			}
		});
		
		
		this.add(panel);		

		this.addButton(btn);
		this.addButton(btnCancel);
	}
		
}
