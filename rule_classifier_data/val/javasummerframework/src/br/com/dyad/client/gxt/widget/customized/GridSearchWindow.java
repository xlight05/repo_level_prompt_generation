package br.com.dyad.client.gxt.widget.customized;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.field.ClientField;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;

public class GridSearchWindow extends Window{

	private DataGridNavigator gridNavigator;
	private ArrayList<DyadBaseModel> models;
	private ComboBox<DyadBaseModel> searchFields;
	private TextField textField;
	private String lastSearchToken;

	public GridSearchWindow( DataGridNavigator gridNavigator ) {
		super();
		this.setTitle("Search");
		this.setWidth(400);
		this.setHeight(200);
		this.setShim(true);
		this.gridNavigator = gridNavigator;
		models = new ArrayList<DyadBaseModel>();
		List<ClientField> fields = gridNavigator.getGrid().getClientFields();
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();			
			if (clientField.getVisible()){
				DyadBaseModel model = new DyadBaseModel();
				model.set("name", clientField.getName());
				models.add(model);
			}	
		}
		
		ListStore<DyadBaseModel> store = new ListStore<DyadBaseModel>();  
		store.add(models);  
		
		FormPanel form = new FormPanel();  
		form.setHeading("Search");
		
		searchFields = new ComboBox<DyadBaseModel>();
		searchFields.setStore(store);
		searchFields.setFieldLabel("Field");  
		searchFields.setDisplayField("name");  
		searchFields.setTriggerAction(TriggerAction.QUERY);
		searchFields.setValue(store.getAt(0));	
		form.add(searchFields);
		
		textField = new TextField();
		textField.setFieldLabel("Search");
		form.add(textField);
		
		Button bSearch = new Button("Search");  
		bSearch.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				String searchToken = (String)textField.getValue();
				if ( searchToken != null ){
					if ( lastSearchToken == null || !searchToken.equals(lastSearchToken) ){
						GridSearchWindow.this.gridNavigator.executeGridSearch((String)searchFields.getValue().get("name"), searchToken);
						lastSearchToken = searchToken; 
					} else {
						GridSearchWindow.this.gridNavigator.executeSearchNextOccur((String)searchFields.getValue().get("name"), searchToken);
					}
				}
			}			
		});
		form.addButton(bSearch);  
		
		form.setButtonAlign(HorizontalAlignment.CENTER);  
		
		this.add(form);
	}

	public DataGridNavigator getGridNavigator() {
		return gridNavigator;
	}

	public void setGridNavigator(DataGridNavigator gridNavigator) {
		this.gridNavigator = gridNavigator;
	}
	
}
