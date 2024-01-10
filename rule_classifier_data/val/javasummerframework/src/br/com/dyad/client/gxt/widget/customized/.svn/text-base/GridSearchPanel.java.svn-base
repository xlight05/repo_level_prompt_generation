package br.com.dyad.client.gxt.widget.customized;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.field.ClientField;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.ui.Label;

public class GridSearchPanel extends ContentPanelCustomized {
	private DataGridNavigator gridNavigator;
	private ArrayList<DyadBaseModel> models;
	private ComboBox<DyadBaseModel> searchFields;
	private TextField textField;
	private String lastSearchToken;
	private String lastFieldName;

	public GridSearchPanel( DataGridNavigator gridNavigator ) {
		super();
		TableLayout layout = new TableLayout();
		layout.setColumns(5);
		this.setLayout(layout);
		this.gridNavigator = gridNavigator;
		this.setHeaderVisible(false);		
		this.setFrame(false);
		
		models = new ArrayList<DyadBaseModel>();		
		
		ListStore<DyadBaseModel> store = new ListStore<DyadBaseModel>();  
		store.add(models);  
		
		//FormPanel form = new FormPanel();  
		//form.setHeaderVisible(false);
		//form.setFrame(false);
		//FormData formData = new FormData();
		Label lab1 = new Label(" Search ");
		lab1.setStyleName("x-form-label-customized");
		add(lab1);
		textField = new TextField(){
			@Override
			protected void onKeyPress(FieldEvent fe) {
				String searchToken = (String)textField.getValue();
				String fieldName = (String)searchFields.getValue().get("name");
				Integer enterKey = 13; //-- Enter
				
				if ( enterKey.equals(fe.getKeyCode()) ){
					if ( searchToken != null ){
						if ( lastSearchToken == null || !searchToken.equals(lastSearchToken) || !fieldName.equals(lastFieldName)){
							GridSearchPanel.this.gridNavigator.executeGridSearch((String)searchFields.getValue().get("name"), searchToken);
							lastSearchToken = searchToken; 
							lastFieldName = fieldName;
						} else {
							GridSearchPanel.this.gridNavigator.executeSearchNextOccur((String)searchFields.getValue().get("name"), searchToken);
						}
					}
				}	
			}
		};
		textField.setWidth(130);
		
		//form.add(textField, formData);
		add(textField);
		
		Label lab2 = new Label(" Column ");
		lab2.setStyleName("x-form-label-customized");
		add(lab2);
		searchFields = new ComboBox<DyadBaseModel>();
		searchFields.setStore(store);
		searchFields.setDisplayField("name");  
		searchFields.setTriggerAction(TriggerAction.QUERY);
		searchFields.setValue(store.getAt(0));		
		searchFields.setWidth(100);
		setGridFieldsStore();
		//form.add(searchFields, formData);
		add(searchFields);
		
		Button bSearch = new Button("Search");  
		bSearch.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				String searchToken = (String)textField.getValue();
				String fieldName = (String)searchFields.getValue().get("name");
				
				if ( searchToken != null ){
					if ( lastSearchToken == null || !searchToken.equals(lastSearchToken) || !fieldName.equals(lastFieldName)){
						GridSearchPanel.this.gridNavigator.executeGridSearch((String)searchFields.getValue().get("name"), searchToken);
						lastSearchToken = searchToken; 
						lastFieldName = fieldName;
					} else {
						GridSearchPanel.this.gridNavigator.executeSearchNextOccur((String)searchFields.getValue().get("name"), searchToken);
					}
				}
			}			
		});
		addButton(bSearch);
	}
	
	public void setGridFieldsStore(){
		List<ClientField> fields = gridNavigator.getGrid().getClientFields();
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();			
			if (clientField.getVisible()){				
				if ( !fieldAdded(clientField.getName()) ){
					DyadBaseModel model = new DyadBaseModel();
					model.set("name", clientField.getName());
					models.add(model);
					searchFields.getStore().add(model);
				}
			}	
		}
		
		ListStore<DyadBaseModel> store = new ListStore<DyadBaseModel>();  
		store.add(models);
		if ( searchFields.getValue() == null && models.size() > 0 ){
			searchFields.setValue(models.get(0));
		}
	}

	public DataGridNavigator getGridNavigator() {
		return gridNavigator;
	}

	public void setGridNavigator(DataGridNavigator gridNavigator) {
		this.gridNavigator = gridNavigator;
	}
	
	private Boolean fieldAdded(String name){
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			DyadBaseModel model = (DyadBaseModel) iterator.next();
			if (((String)model.get("name")).equals(name)){
				return true;
			}
		}
		return false;
	}

	public ComboBox<DyadBaseModel> getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(ComboBox<DyadBaseModel> searchFields) {
		this.searchFields = searchFields;
	}

	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}
	
	
}
