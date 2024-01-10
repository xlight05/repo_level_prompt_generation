package br.com.dyad.client.widget.field;

import java.util.Arrays;
import java.util.HashMap;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.widget.customized.DialogCustomized;
import br.com.dyad.client.gxt.widget.customized.FormPanelCustomized;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.grid.ClientDataGrid;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class ClientField extends DyadBaseModel {
	
	public abstract void setOldRawValue(String value);		
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("value" ) && !(this instanceof ClientComboField)) {
			setOldRawValue( value != null ? value.toString() : "" );
			clearInvalid();
			
			this.getFormViewField().setValue(value);
			this.getTableViewField().setValue(value);
		} else if (name.equals("isCalculated")) {
			if (getIsCalculated() != null && getIsCalculated()){
				if (getFormViewField() != null) {					
					getFormViewField().setStyleAttribute("background-color", "#FFE000");
				}
			}			
		} else if (name.equals("label")) {
			if ( getLabelField() == null ){
				setLabelField(new Label());
				getLabelField().setStyleName("x-form-label-customized");
			}
			getLabelField().setText((String)value);		

			if (getFormViewField() != null) {				
				getFormViewField().setFieldLabel((String)value);
			}
			if (getTableViewField() != null) {				
				getTableViewField().setFieldLabel((String)value);
			}
		} else if (name.equals("width")) {
			if (getFormViewField() != null) {				
				getFormViewField().setWidth((Integer)value);
			}
		} else if (name.equals("tableViewWidth")) {
			if (getTableViewField() != null) {				
				getTableViewField().setWidth((Integer)value);
			}
		} else if (name.equals("name")) {
			if (getFormViewField() != null) {				
				getFormViewField().setName((String)value);
			}
			if (getTableViewField() != null) {				
				getTableViewField().setName((String)value);
			}
		} else if (name.equals("visible")) {
			Boolean visible = (Boolean)value;
			
			if ( visible != null && visible ){
				if (getFormViewField() != null) {					
					getFormViewField().show();
				}
			} else {
				if (getFormViewField() != null) {					
					getFormViewField().hide();
				}
			}
		} else if (name.equals("tableViewVisible")) {
			Boolean tableViewVisible = (Boolean)value;
			
			if ( tableViewVisible != null && tableViewVisible ){
				if (getTableViewField() != null) {					
					getTableViewField().show();
				}
				
				if ( this.getColumnConfig() != null ){
					this.getColumnConfig().setHidden(false);
				}	
			} else {
				if (getTableViewField() != null) {					
					getTableViewField().hide();
				}
				if ( this.getColumnConfig() != null ){
					this.getColumnConfig().setHidden(true);
				}	
			}
		} else if (name.equals("readOnly")) {
			if ( getReadOnly() ){	
				if (getFormViewField() != null) {					
					getFormViewField().disable();
				}
				if (getTableViewField() != null) {					
					getTableViewField().disable();
				}
			} else {
				if (getFormViewField() != null) {					
					getFormViewField().enable();
				}
				if (getTableViewField() != null) {					
					getTableViewField().enable();
				}
			}
		} else if (name.equals("required")) {
			Boolean boolValue = (Boolean)value;
			setRequired(boolValue);
		}
	}
	
	public Field getFormViewField() {
		return get("formViewField");
	}
	
	public void setFormViewField(Field formViewField) {
		set("formViewField", formViewField);
	}
	
	public Field getTableViewField() {
		return get("tableViewField");
	}
	
	public void setTableViewField(Field tableViewField) {
		set("tableViewField", tableViewField);
	}
	
	public Boolean getIsCalculated() {
		return get("isCalculated");
	}

	public void setIsCalculated(Boolean isCalculated) {
		set("isCalculated", isCalculated);
	}

	public String getServerObjectId() {
		return get("objectId");
	}
	
	public void setServerObjectId(String serverObjectId) {
		set("objectId", serverObjectId);
	}

	public Integer getOrder() {
		return get("order");
	}

	public void setOrder(Integer order) {
		set("order", order);
	}

	public Integer getColumn() {
		return get("column");
	}

	public void setColumn(Integer column) {
		set("column", column);
	}
	
	public Integer getColSpan() {
		return get("colSpan");
	}

	public void setColSpan(Integer colSpan) {
		set("colSpan", colSpan);
	}

	public String getGroup() {
		return get("group");
	}

	public void setGroup(String group) {
		set("group", group);
	}

	public String getHelp() {
		return get("help");
	}

	public void setHelp(String help) {
		set("help", help);
	}

	public Integer getWidth() {
		return get("width");
	}

	public void setWidth(Integer width) {
		set("width", width);		
	}
	
	public Integer getTableViewWidth() {
		return get("tableViewWidth");
	}

	public void setTableViewWidth(Integer tableViewWidth) {
		set("tableViewWidth", tableViewWidth);
	}
	
	
	public void setRequired(Boolean required){
		if ( required != null ){
			if ( required ){
				if ( getLabel() != null && getLabel().charAt(getLabel().length()-1) != '*' ){
					this.setLabel(getLabel() + "*");
				}
			} else {
				if ( getLabel() != null && getLabel().charAt(getLabel().length()-1) == '*' ){
					setLabel(getLabel().substring(0, getLabel().length()-1));
				}
			}
		}	
	}

	public String getLabel() {
		return get("label");
	}

	public void setLabel(String label) {
		set("label", label);
	}
		
	
	public static void tryDispatchFirstActionIfFieldIsLastField(FieldEvent fe, Widget field, Integer trigger){
 		if ( trigger.equals(fe.getKeyCode()) ){
 			System.out.println("deu um enter");
 			FormPanelCustomized formViewPanel = (FormPanelCustomized)field.getParent(); 
 			ClientGrid grid = (formViewPanel).getClientGrid();
 			if ( grid instanceof ClientVariableGrid && formViewPanel.getFields().get( formViewPanel.getFields().size() -1 ) == field ){
 				System.out.println("o ultimo e deu um enter");
 				ClientProcess process = grid.getProcess();
 				ClientAction action = process.getFirstEnabledAndVisibleAction();
 				if ( action != null ){
 					System.out.println("Disparou o click..");
 					action.onClick();
 				}
 			}
 		}
	}

	public static void dispatchGetFieldInformation( FieldEvent fe, Widget field, ClientField clientField, Integer trigger ){
 		System.out.println("KeyCode=" + fe.getKeyCode());
		if ( trigger.equals(fe.getKeyCode()) ){
 			FormPanelCustomized formViewPanel = (FormPanelCustomized)field.getParent(); 
 			ClientGrid grid = (formViewPanel).getClientGrid();
            ClientProcess process = grid.getProcess();
            process.getObjectSincronizer().dispatchGetFieldInformation( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId() );
 		}
	}

	@SuppressWarnings("unchecked")
	public static void goToNextFieldOnKeyPress(FieldEvent fe, Widget field, Integer trigger){
		if ( trigger.equals(fe.getKeyCode()) ){
 			FormPanelCustomized formViewPanel = (FormPanelCustomized)field.getParent(); 	
			int index = formViewPanel.getFields().indexOf(field) + 1;
			if ( index <= formViewPanel.getFields().size() - 1 ){
				Field fld = formViewPanel.getFields().get( index );
				int i = 0;
				while ( ( fld instanceof LabelField || ! fld.isEnabled() || ! fld.isVisible() )  && i < formViewPanel.getFields().size() ){
					try {
						fld = formViewPanel.getFields().get( formViewPanel.getFields().indexOf(field) + 1 + i );
						i++;
					} catch(Exception e){
						break;
					}
				}
				if ( fld != null ){
					fld.focus();
				}	
			}
		}
	}

	public ClientGrid getGrid() {
		return get("grid");
	}

	public void setGrid(ClientGrid grid) {
		set("grid", grid);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);		
	}

	public Boolean getVisible() {
		return get("visible") == null ? false : (Boolean)get("visible");
	}

	public void setVisible(Boolean visible) {
		set("visible", visible);		
	}

	public Label getLabelField() {
		return get("labelField");
	}

	public void setLabelField(Label labelField) {
		set("labelField", labelField);
	}


	@SuppressWarnings("unchecked")
	public static void synchronizeFieldWithServerInformation(ClientProcess clientProcess, ClientGrid clientGrid, HashMap configField, HashMap configGrid ) throws Exception {		
		String fieldServerObjectId = (String)configField.get("objectId");
		
		if ( fieldServerObjectId == null || fieldServerObjectId.equals("") ){
			throw new Exception("Field server object id must not be null or empty.");
		} 
			
		ClientField clientField = clientGrid.getFieldByObjectId( fieldServerObjectId );		
		Integer fieldType = (Integer)configField.get("type");		
		Integer width = (Integer)configField.get("width");
		Integer tableViewWidth = (Integer)configField.get("tableViewWidth");
							
		if (clientField == null) {
			clientField = ClientFieldSynchronizer.getClientFieldSynchronizer().newField(clientProcess, clientGrid, fieldType, configField);
			
			if ( fieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) ) {				
				((ClientMasterDetailField)clientField).getClientDataGrid().createTableViewGrid();
				
				if ( !configField.containsKey("label") ){
					clientField.setLabel((String)configField.get("name"));
				}
				
			} else {				
				clientGrid.add( clientField );				
			}
			
		} else {									
			clientField.setDefaultProperties(configField);
		}
		
		if ( fieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) ) {
			if ( configField.keySet().contains("dataGrid") ){
				ClientGrid.sincronizeGridDetailWithServerInformation( clientProcess, ((ClientMasterDetailField)clientField), (HashMap)configField.get("dataGrid") );
			}
		}
		
		if ( configField.keySet().contains("fieldValueAsDefault") ){
			ClientVariableGrid grd = (ClientVariableGrid)clientGrid;
			grd.addFieldValueToSave(clientField);
		}
		verifyGroupCreation( clientField, clientGrid, configField, clientProcess );
				
	}

	@SuppressWarnings("unchecked")
	public static HashMap getFieldsColSpan( HashMap configGrid, Integer lastColumn ){
		Integer size = (Integer)configGrid.get("fieldCount");
		Integer colSpan = lastColumn;
		HashMap colSpanConfig = new HashMap();
		//String[] fieldList = new String[size];
		Integer visibleFieldsCount = 0;
		
		for (int i = 0; i < size; i++) {			
			Object ob;			
			ob = configGrid.get("FIELD" + i);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					if ( configField.get("visible") != null && (Boolean)configField.get("visible")){
						visibleFieldsCount++;
					}	
				}	
			}		
		}
		String[] fieldList = new String[visibleFieldsCount];
		visibleFieldsCount = 0;
		
		for (int i = 0; i < size; i++) {			
			Object ob;			
			ob = configGrid.get("FIELD" + i);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					if ( configField.get("visible") != null && (Boolean)configField.get("visible")){
						Integer order = (Integer)configField.get("order");
						Integer fieldOrder = ( order == null || ( i > 0 && order == 0 ) ? i : order );
						String strFieldOrder = "" + fieldOrder;
						if ( strFieldOrder.length() < 20 ){
							int length = 20 - strFieldOrder.length();
							String complement = "";
							for (int j = 0; j < length; j++) {
								complement += "0";
							}
							strFieldOrder = complement + strFieldOrder;
						}
						fieldList[visibleFieldsCount] = ( strFieldOrder + "FIELD" + i );
						visibleFieldsCount++;
					}	
				}	
			}		
		}
		
		Arrays.sort(fieldList);

		for (int i = 0; i < fieldList.length; i++) {
			String field = fieldList[i].substring(fieldList[i].indexOf("FIELD"));			
			Object ob;			
			ob = configGrid.get(field);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					Integer fieldColumn = (Integer)configField.get("column");
					fieldColumn = fieldColumn == null ? 0 : fieldColumn;
					Integer fieldType = (Integer)configField.get("type");
					
					if ( ( fieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) || 
							fieldColumn == null || fieldColumn == 0 ) && colSpan > 1 && i > 0){						
						String previousField = fieldList[i-1].substring(fieldList[i-1].indexOf("FIELD"));
						HashMap obj = (HashMap)configGrid.get(previousField);
						System.out.println("CAMPO: " + (String)obj.get("name"));
						if ( (Integer)obj.get("column") + 1 < lastColumn ){							
							Integer previousFieldType = (Integer)obj.get("type");
							if ( !previousFieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL)){
								colSpanConfig.put((String)obj.get("name"), colSpan);
								System.out.println("COLSPAN FIELD: " + (String)obj.get("name") + " - " + colSpan );
							}
						}							
						colSpan = lastColumn;					
					} else {
						if ( i > 0 ){
							if ( fieldColumn + 1 == lastColumn ){
								colSpan = lastColumn;
							} else {								
								colSpan--;
							}							
						}
					}
					
					if ( fieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) ){
						System.out.println("COLSPAN DETAIL: " + (lastColumn * 2));
						colSpanConfig.put((String)configField.get("name"), lastColumn * 2 );
					}
				}	
			}
		}							
		return colSpanConfig;
	}


	@SuppressWarnings("unchecked")
	public static void showFieldInformation(HashMap resultMap) {
		String text = "";
		
		String help = (String)resultMap.get("Help"); 
		if ( help != null && ! help.equals("") ){
			text += "Help: " + help + "\r\n";
		} else {
			text += "Help: No help defined to this field.\r\n";
		}

		text += "Order: " + (Integer)resultMap.get("Order") + "\r\n";
		text += "Name: " + (String)resultMap.get("Name") + "\r\n";
		text += "Grid Entity: " + (String)resultMap.get("Entity") + "\r\n";
		text += "Grid: " + (String)resultMap.get("Grid") + "\r\n";
		text += "Type: " + (String)resultMap.get("Type") + "\r\n";

		if ( resultMap.containsKey("Lookup to") ){
			text += "Lookup to: " + (String)resultMap.get("Lookup to") + "\r\n";
		}
		
		String group = (String)resultMap.get("Group"); 
		if ( group != null && ! group.equals("") ){
			text += "Group: " + (String)resultMap.get("Group") + "\r\n";
		}
		
		
		final DialogCustomized simple = new DialogCustomized( "Field Help" );
		simple.addText( text.replace( "\r\n" , "<br>") );
		simple.show();		
	}

	@SuppressWarnings("unchecked")
	public void setTableViewValue( Object value ){		
		ClientGrid clientGrid = this.getGrid();
		if ( clientGrid instanceof ClientDataGrid ){
			ClientDataGrid clientDataGrid = (ClientDataGrid) clientGrid;
			if ( clientDataGrid.getIdCurrentEntity() != null ){
				ListStore store = clientDataGrid.getTableViewStore();
				if ( store.getModels().size() > 0 ){
					DyadBaseModel model = (DyadBaseModel) store.findModel("id", clientDataGrid.getIdCurrentEntity() );
					if ( model != null ){
						model.set(this.getName(), value);
						store.update(model);
					}	
				}
			}	
		}
	}

	public Boolean getTableViewVisible() {
		return get("tableViewVisible");
	}

	public void setTableViewVisible(Boolean tableViewVisible) {
		set("tableViewVisible", tableViewVisible);		
	}

	public void clearInvalid() {
		getFormViewField().clearInvalid();
		getTableViewField().clearInvalid();
	}
	
	public void setRawValue(String value) {
		getFormViewField().setRawValue( value );
		getTableViewField().setRawValue( value );
	}	

	public ColumnConfig getColumnConfig(){
		return null;
	}
	
	public void setReadOnly(Boolean readOnly) {
		set("readOnly", readOnly);		
	}
	
	public Boolean getReadOnly() {
		return get("readOnly");
	}

	public Boolean getSaveLastValueAsDefault() {
		return get("saveLastValueAsDefault");
	}

	public void setSaveLastValueAsDefault(Boolean saveLastValueAsDefault) {
		set("saveLastValueAsDefault", saveLastValueAsDefault);
	}
	
	public String getFieldValueAsDefault() {
		return get("fieldValueAsDefault");
	}

	public void setFieldValueAsDefault(String fieldValueAsDefault) {
		set("fieldValueAsDefault", fieldValueAsDefault);
	}

	public Long getFieldIdValueAsDefault() {
		return get("fieldIdValueAsDefault");
	}

	public void setFieldIdValueAsDefault(Long fieldIdValueAsDefault) {
		set("fieldIdValueAsDefault", fieldIdValueAsDefault);
	}

	public Boolean getReadOnlyFromClass() {
		return get("readOnlyFromClass");
	}

	public void setReadOnlyFromClass(Boolean readOnlyFromClass) {
		set("readOnlyFromClass", readOnlyFromClass);
	}

	public Boolean getCanCustomizeValue() {
		return get("canCustomizeValue");
	}

	public void setCanCustomizeValue(Boolean canCustomizeValue) {
		set("canCustomizeValue", canCustomizeValue);
	}
	
	@SuppressWarnings("unchecked")
	public static void verifyGroupCreation(ClientField clientField, ClientGrid clientGrid, HashMap configField, ClientProcess clientProcess) {
		String group = (String)configField.get("group");
		if ( group != null && ! group.equals("") ){
			clientField.setGroup(group);
			if (clientProcess.getLastGroup() == null || ! clientProcess.getLastGroup().equals(group) ){
				clientProcess.setLastGroup(group);
				ClientLabelGroup clientLabelGroup = new ClientLabelGroup(group);
				clientGrid.add( clientLabelGroup.widget );
			}
		}					
	}
}