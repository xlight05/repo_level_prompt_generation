package br.com.dyad.client.widget.field;

import java.util.Arrays;
import java.util.HashMap;

import com.extjs.gxt.ui.client.widget.layout.TableLayout;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.grid.ClientDataGrid;
import br.com.dyad.client.widget.grid.ClientGrid;

public class ClientFieldSynchronizer {
	
	public static ClientFieldSynchronizer clientFieldSynchronizer;
	
	public static ClientFieldSynchronizer getClientFieldSynchronizer() {
		if (clientFieldSynchronizer == null) {
			clientFieldSynchronizer = new ClientFieldSynchronizer();
		}
		return clientFieldSynchronizer;
	}
	
	private ClientFieldSynchronizer() {
		
	}
	
	/**
	 * Retorna uma instância de field com as propriedades do HashMap preenchidas
	 * 
	 * @param fieldType
	 * @param params
	 * @return
	 */
	public ClientField newField(ClientProcess clientProcess, ClientGrid clientGrid, Integer fieldType, HashMap params) {
		ClientField field = null;
		
		if (FieldTypes.FIELD_TYPE_BOOLEAN.equals(fieldType)) {
			field = new ClientBooleanField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_COMBO.equals(fieldType)) {
			field = new ClientComboField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_HTML_EDITOR.equals(fieldType)) {
			field = new ClientHtmlEditorField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_INTEGER.equals(fieldType)) {
			field = new ClientIntegerField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_LABEL.equals(fieldType)) {
			field = new ClientLabelGroup((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_LOOKUP.equals(fieldType) || FieldTypes.FIELD_TYPE_CLASS_LOOKUP.equals(fieldType) ) {
			field = new ClientLookupField((String)params.get("name"), (String)params.get("beanName"));						
		} else if (FieldTypes.FIELD_TYPE_MASTERDETAIL.equals(fieldType)) {		
			Integer width = (Integer)params.get("width");
			Integer tableViewWidth = (Integer)params.get("tableViewWidth");
			
			String fieldServerObjectId = (String)params.get("objectId");
			HashMap detailGridConfig = null;
			if ( params.keySet().contains("dataGrid") ){
				detailGridConfig = (HashMap)params.get("dataGrid");
			}
			
			Integer lastColumn = ClientGrid.getGridColumnCount( detailGridConfig );
			Integer columnCount = lastColumn == 1 ? 2 : ( lastColumn * 2 );
			
			field = new ClientMasterDetailField( clientProcess, (String)params.get("name"), columnCount, ClientField.getFieldsColSpan(detailGridConfig, lastColumn) );
			
			TableLayout layout = new TableLayout();	
			layout.setColumns(columnCount );					
			((ClientMasterDetailField)field).getClientDataGrid().getFormViewPanel().setLayout(layout);
			
			field.setServerObjectId( fieldServerObjectId );
			
			if ( width != null ){ 
				field.setWidth(width); 
			}
			
			if ( tableViewWidth != null ){ 
				field.setTableViewWidth(tableViewWidth); 
			}			
			
			field.setDefaultProperties(params);
			
			((ClientDataGrid)clientGrid).add( ((ClientMasterDetailField)field).getClientDataGrid(), ((ClientMasterDetailField)field) );
			((ClientMasterDetailField)field).getClientDataGrid().setProcess( clientGrid.getProcess() );
		} else if (FieldTypes.FIELD_TYPE_MEMO.equals(fieldType)) {
			field = new ClientMemoField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_NUMBER.equals(fieldType)) {
			field = new ClientNumberField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_SIMPLEDATE.equals(fieldType)) {
			field = new ClientSimpleDateField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_STRING.equals(fieldType)) {
			field = new ClientStringField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_TIME.equals(fieldType)) {
			field = new ClientTimeField((String)params.get("name"));
		} else if (FieldTypes.FIELD_TYPE_UPLOAD.equals(fieldType)) {
			field = new ClientUploadField((String)params.get("name"));
		}
		
		setClientFieldLabel(params, field);
		setClientFieldVisible(params, field);
		
		if (field != null) {
			field.setDefaultProperties(params);
		}
		
		return field;
	}
	
	@SuppressWarnings("unchecked")
	public void setClientFieldLabel(HashMap configField, ClientField clientField){
		if ( configField.containsKey("label") ){
			clientField.setLabel((String)configField.get("label"));
		} else {
			if ( configField.containsKey("name") && clientField.getLabel() == null ){
				clientField.setLabel( clientField.getName() );
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setClientFieldVisible( HashMap configField, ClientField clientField ){
		Boolean visible = (Boolean)configField.get("visible");
		if ( visible != null ){
			clientField.setVisible(visible);

		}		
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap getFieldsColSpan( HashMap configGrid, Integer lastColumn ){
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

}
