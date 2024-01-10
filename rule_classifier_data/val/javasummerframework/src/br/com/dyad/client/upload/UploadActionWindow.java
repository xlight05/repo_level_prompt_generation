package br.com.dyad.client.upload;

import java.util.HashMap;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.gxt.field.customized.UploadFieldCustomized;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;

public class UploadActionWindow extends Window {
	
	private Button btCancel;
	private Button btUpload;
	private Long pictureId;
	private UploadFieldCustomized uploadField;
	private int x;
	private int y;
	private ContentPanel picturePanel;
	
	
	public UploadActionWindow(UploadFieldCustomized uploadField, int x, int y) {
		this.uploadField = uploadField;
		this.x = x;
		this.y = y;
		setPosition(x, y);		
		setWidth(300);
		setModal(true);
		setHeight(200);
		
		//setLayout(new FormLayout());
				
		//picturePanel.setStyleAttribute("background", value);
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("picture", uploadField.getRawValue());
		
		proxy.getServiceValue("br.com.dyad.infrastructure.service.GetDatabaseFile", params, new DyadAsyncCallback(){
			@Override
			public void onSuccess(Object arg0) {
				UploadActionWindow.this.picturePanel = new ContentPanel();
				UploadActionWindow.this.picturePanel.setWidth(UploadActionWindow.this.getWidth() - 30);
				UploadActionWindow.this.picturePanel.setHeight(UploadActionWindow.this.getHeight() - 40);				
				
				HashMap map = (HashMap)arg0;
				String picture = (String)map.get("picture");
				
				UploadActionWindow.this.picturePanel.setStyleAttribute("background", picture);
				UploadActionWindow.this.add(UploadActionWindow.this.picturePanel);
				
				
				//UploadActionWindow.this.layout();
			}
		});
				
		btUpload = new Button(DyadInfrastructure.translate("Upload"));
		btUpload.setHeight(25);
		btUpload.setWidth(70);
		
		btUpload.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UploadActionWindow.this.setVisible(false);
				
				UploadWindow uploadWindow = new UploadWindow(UploadActionWindow.this.uploadField);
				uploadWindow.setPosition(UploadActionWindow.this.x, UploadActionWindow.this.y);
				uploadWindow.setVisible(true);
			}
		});
		
		btCancel = new Button(DyadInfrastructure.translate("Cancel"));
		btCancel.setHeight(25);
		btCancel.setWidth(70);
		
		btCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UploadActionWindow.this.setVisible(false);
			}
		});
		
		addButton(btUpload);
		addButton(btCancel);
	}
	
	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

}
